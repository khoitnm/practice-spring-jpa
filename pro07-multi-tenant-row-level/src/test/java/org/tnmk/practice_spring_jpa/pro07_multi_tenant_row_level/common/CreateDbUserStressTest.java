package org.tnmk.practice_spring_jpa.pro07_multi_tenant_row_level.common;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;
import org.tnmk.practice_spring_jpa.pro07_multi_tenant_row_level.common.test_target.DbUserService;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@SpringBootTest
class CreateDbUserStressTest {

    public static final int SLOW_TENANT_THRESHOLD_MILLS = 200;
    @Autowired
    DataSource dataSource;

    @Autowired
    private DbUserService dbUserService;

    @DisplayName("This test will show how MultiTenantConnectionProviderImpl can handle many parallel requests at the same time." +
        "Right now, it's getting dead lock because MS SQL Server cannot handle too many DDL in parallel (it may get dead lock, and lead to connection timeout), will need to improve.")
    @Test
    void stressTest() throws InterruptedException {
        // GIVEN
        final AtomicInteger errorsCount = new AtomicInteger(0);
        // When increase the number of threads to 50, tenantsPerThread to 100, some connection will get request timeout.
        int numberOfThreads = 50;
        int loopsPerThread = 100;
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        List<String> slowDbUsers = new ArrayList<>();
        // WHEN
        for (int i = 0; i < numberOfThreads; i++) {
            final int threadIndex = i;
            new Thread(() -> {
                try {
                    for (int j = 0; j < loopsPerThread; j++) {
                        String dbUsername = "stressDbUser-%s-%s".formatted(threadIndex, j);
                        StopWatch stopWatch = new StopWatch();
                        stopWatch.start(dbUsername);
                        try (Connection connection = dataSource.getConnection();) {
                            dbUserService.createDbUser(connection, dbUsername);
                        } catch (Exception e) {
                            errorsCount.incrementAndGet();
                            log.error("Creating DbUser failed: {}", dbUsername, e);
                        }
                        stopWatch.stop();
                        log.info("Creating DbUser successfully {} in {} ms", dbUsername, stopWatch.getTotalTimeMillis());
                        if (stopWatch.getTotalTimeMillis() > SLOW_TENANT_THRESHOLD_MILLS) {
                            slowDbUsers.add(dbUsername + " took " + stopWatch.getTotalTimeMillis() + " ms");
                            log.warn("Creating DbUser {} took too long: {} ms", dbUsername, stopWatch.getTotalTimeMillis());
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
        log.info("Slow DbUser: " + slowDbUsers.size() + " \n"
            + String.join(",\n", slowDbUsers)
        );

        Assertions.assertThat(errorsCount.get()).as("There should be no error when creating DbUser.").isEqualTo(0);
    }

}
