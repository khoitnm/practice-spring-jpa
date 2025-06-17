package org.tnmk.practice_spring_jpa.pro07_multi_tenant_row_level.support_for_testing.stresstest;

import lombok.extern.slf4j.Slf4j;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
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
                                log.error("Error in thread {} at loop {}: {}", threadIndex, loopIndex, e.getMessage(), e);
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

            logSystemResources();

            // Report
            log.info("""
                    Report:
                        Total runs: {} (threads: {}, loops per thread: {})
                        Success: {}
                        Errors: {}
                        Average runtime: {} ms
                        80th percentile runtime: {} ms
                        90th percentile runtime: {} ms
                        Slowest runtimes: {}
                        Fastest runtimes: {}
                    """,
                totalRuns, threads, loopPerThread,
                successCount.get(),
                errorCount.get(),
                averageRuntime,
                runtime80Percentile,
                runtime90Percentile,
                getBottom(runtimes, 10),
                getTop(runtimes, 10)
            );

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

    public static <E> List<E> getBottom(List<E> list, int n) {
        int topCount = Math.min(n, list.size());
        return list.subList(list.size() - topCount, list.size());
    }

    public static <E> List<E> getTop(List<E> list, int n) {
        int topCount = Math.min(n, list.size());
        return list.subList(0, topCount);
    }

    private static void logSystemResources() {
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
        long totalMemory = Runtime.getRuntime().totalMemory();
        long freeMemory = Runtime.getRuntime().freeMemory();
        int availableProcessors = osBean.getAvailableProcessors();

        log.info("""
                System Resources:
                    OS Name: %s
                    OS Version: %s
                    Available Processors: %s
                    Total Memory (GB): %.2f
                    Free Memory (GB): %.2f
                    JVM Uptime (ms): %s""".formatted(
                osBean.getName(),
                osBean.getVersion(),
                availableProcessors,
                totalMemory * 1.0 / (1024 * 1024 * 1024),
                freeMemory * 1.0 / (1024 * 1024 * 1024),
                runtimeBean.getUptime()
            )
        );
    }
}