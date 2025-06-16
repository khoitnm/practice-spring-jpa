package org.tnmk.practice_spring_jpa.pro07_multi_tenant_row_level.support_for_testing;

import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class StressTestHelper {
    /**
     * @param threads
     * @param loopPerThread
     * @param function      first argument is the thread index, second argument is the loop index.
     */
    public static StressTestResult run(int threads, int loopPerThread, ThrowingBiConsumer<Integer, Integer> function) {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor();) {
            var phaser = new Phaser(1); // Phaser to wait for all threads to finish
            List<Long> runtimes = Collections.synchronizedList(new ArrayList<>());
            AtomicInteger successCount = new AtomicInteger();
            AtomicInteger errorCount = new AtomicInteger();

            for (int i = 0; i < threads; i++) {
                final int threadIndex = i;
                phaser.register();
                executor.submit(() -> {
                    try {
                        for (int loopIndex = 0; loopIndex < loopPerThread; loopIndex++) {
                            Instant start = Instant.now();
                            try {
                                function.accept(threadIndex, loopIndex);
                                successCount.incrementAndGet();
                            } catch (Exception e) {
                                errorCount.incrementAndGet();
                            }
                            Instant end = Instant.now();
                            runtimes.add(Duration.between(start, end).toMillis());
                        }
                    } finally {
                        phaser.arriveAndDeregister();
                    }
                });
            }

            phaser.arriveAndAwaitAdvance(); // Wait for all threads to finish
            executor.shutdown();

            // Calculate statistics
            Collections.sort(runtimes);
            long totalRuns = runtimes.size();
            double averageRuntime = runtimes.stream().mapToLong(Long::longValue).average().orElse(0);
            long runtime90Percentile = runtimes.get((int) (totalRuns * 0.9) - 1);
            long runtime80Percentile = runtimes.get((int) (totalRuns * 0.8) - 1);

            // Report
            log.info("Report:");
            log.info("Total runs: {}", totalRuns);
            log.info("Success: {}", successCount.get());
            log.info("Errors: {}", errorCount.get());
            log.info("Average runtime: {} ms", averageRuntime);
            log.info("80th percentile runtime: {} ms", runtime80Percentile);
            log.info("90th percentile runtime: {} ms", runtime90Percentile);
            // Log top 3 slowest runtimes
            int topCount = Math.min(3, runtimes.size());
            List<Long> topSlowestRuntimes = runtimes.subList(runtimes.size() - topCount, runtimes.size());
            log.info("Top {} slowest runtimes: {}", topCount, topSlowestRuntimes);

            // Create and return the report
            return new StressTestResult(
                totalRuns,
                successCount.get(),
                errorCount.get(),
                averageRuntime,
                runtime90Percentile,
                runtime80Percentile
            );
        }
    }

    @FunctionalInterface
    public interface ThrowingBiConsumer<T, U> {
        void accept(T t, U u) throws Exception;
    }
}