package org.tnmk.practice_spring_jpa.pro07_multi_tenant_row_level.business;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.tnmk.practice_spring_jpa.pro07_multi_tenant_row_level.common.multitenant.MultiTenantConnectionProviderImpl;

import java.sql.Connection;
import java.util.concurrent.CountDownLatch;

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
        int numberOfThreads = 50;
        int tenantsPerThread = 50;
        CountDownLatch latch = new CountDownLatch(numberOfThreads);

        // WHEN
        for (int i = 0; i < numberOfThreads; i++) {
            final int threadIndex = i;
            new Thread(() -> {
                try {
                    for (int j = 0; j < tenantsPerThread; j++) {
                        String tenantId = "stressTenant-%s-%s".formatted(threadIndex, j);

                        try (Connection connection = multiTenantConnectionProvider.getConnection(tenantId);) {
                            log.info("Successfully got connection for tenantId: {}", tenantId);
                        } catch (Exception e) {
                            log.error("Error while getting connection for tenant: {}", tenantId, e);
                        }
                        // after this, connection will be closed automatically
                    }
                } catch (Exception e) {
                    log.error("Error in thread {}: {}", threadIndex, e.getMessage(), e);
                } finally {
                    latch.countDown();
                }
            }).start();
        }

        // THEN
        // This test is to ensure that the MultiTenantConnectionProviderImpl can handle a high load of requests.
        // You can add assertions or logging to verify the behavior under stress.
        latch.await(); // Wait for all threads to finish
        log.info("All threads have completed execution.");
    }

}
