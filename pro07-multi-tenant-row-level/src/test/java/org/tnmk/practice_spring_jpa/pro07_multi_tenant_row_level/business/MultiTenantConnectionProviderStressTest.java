package org.tnmk.practice_spring_jpa.pro07_multi_tenant_row_level.business;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.tnmk.practice_spring_jpa.pro07_multi_tenant_row_level.common.multitenant.MultiTenantConnectionProviderImpl;

import java.sql.Connection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@SpringBootTest
class MultiTenantConnectionProviderStressTest {

    @Autowired
    private MultiTenantConnectionProviderImpl multiTenantConnectionProvider;

    @DisplayName("This test will show how MultiTenantConnectionProviderImpl can handle many parallel requests at the same time." +
        "Right now, it's getting dead lock because MS SQL Server cannot handle too many DDL in parallel (it may get dead lock, and lead to connection timeout), will need to improve.")
    @Test
    void stressTest() throws InterruptedException {
        // GIVEN
        final AtomicInteger errorsCount = new AtomicInteger(0);
        int numberOfThreads = 30;
        int tenantsPerThread = 10;
        CountDownLatch latch = new CountDownLatch(numberOfThreads);

        // WHEN
        for (int i = 0; i < numberOfThreads; i++) {
            final int threadIndex = i;
            new Thread(() -> {
                try {
                    for (int j = 0; j < tenantsPerThread; j++) {
                        String tenantId = "stressTenant-%s-%s".formatted(threadIndex, j);

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
    }

}
