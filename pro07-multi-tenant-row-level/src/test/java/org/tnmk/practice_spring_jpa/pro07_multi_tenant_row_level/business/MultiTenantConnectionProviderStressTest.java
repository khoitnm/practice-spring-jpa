package org.tnmk.practice_spring_jpa.pro07_multi_tenant_row_level.business;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;
import org.tnmk.practice_spring_jpa.pro07_multi_tenant_row_level.common.multitenant.MultiTenantConnectionProviderImpl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@SpringBootTest
class MultiTenantConnectionProviderStressTest {

    public static final int SLOW_TENANT_THRESHOLD_MILLS = 200;
    @Autowired
    private MultiTenantConnectionProviderImpl multiTenantConnectionProvider;

    @DisplayName("This test will show how MultiTenantConnectionProviderImpl can handle many parallel requests at the same time." +
        "Right now, it's getting dead lock because MS SQL Server cannot handle too many DDL in parallel (it may get dead lock, and lead to connection timeout), will need to improve.")
    @Test
    void stressTest() throws InterruptedException {
        // GIVEN
        final AtomicInteger errorsCount = new AtomicInteger(0);
        // When increase the number of threads to 50, tenantsPerThread to 100, some connection will get request timeout.
        int numberOfThreads = 50;
        int tenantsPerThread = 100;
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        List<String> slowTenants = new ArrayList<>();
        // WHEN
        for (int i = 0; i < numberOfThreads; i++) {
            final int threadIndex = i;
            new Thread(() -> {
                try {
                    for (int j = 0; j < tenantsPerThread; j++) {
                        String tenantId = "stressTenant-%s-%s".formatted(threadIndex, j);
                        StopWatch stopWatch = new StopWatch();
                        stopWatch.start(tenantId);
                        try (Connection connection = multiTenantConnectionProvider.getConnection(tenantId);) {
                            /**
                             * Just closing connection alone is not enough, we need to call `releaseConnection` to REVERT the user context (set by `EXECUTED AS...`) from the connection.
                             * If we don't do that, it will get error for the next thread (please view more in {@link MultiTenantConnectionProviderImpl#releaseAnyConnection(Connection)}.
                             * Question: what if business level get some error, can it REVERT the connection?
                             */
                            multiTenantConnectionProvider.releaseConnection(tenantId, connection);
                        } catch (Exception e) {
                            errorsCount.incrementAndGet();
                            log.error("Error while getting connection for tenant: {}", tenantId, e);
                        }
                        stopWatch.stop();
                        log.info("Finished getting connection for tenant {} in {} ms", tenantId, stopWatch.getTotalTimeMillis());
                        if (stopWatch.getTotalTimeMillis() > SLOW_TENANT_THRESHOLD_MILLS) {
                            slowTenants.add(tenantId + " took " + stopWatch.getTotalTimeMillis() + " ms");
                            log.warn("Tenant {} took too long to get and release connection: {} ms", tenantId, stopWatch.getTotalTimeMillis());
                        }
                    }
                } catch (Exception e) {
                    log.error("Error in thread {}: {}", threadIndex, e.getMessage(), e);
                    errorsCount.incrementAndGet();
                } finally {
                    latch.countDown();
                }
            }).start();
        }

        // THEN
        latch.await(); // Wait for all threads to finish
        log.info("All threads have completed execution.");
        Assertions.assertThat(errorsCount.get()).as("There should be no error when getting connection for tenant.").isEqualTo(0);
        log.info("Slow tenants: " + slowTenants.size() + " \n"
            + String.join(",\n", slowTenants)
        );
    }

}
