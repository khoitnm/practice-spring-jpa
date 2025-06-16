package org.tnmk.practice_spring_jpa.pro07_multi_tenant_row_level.support_for_testing.stresstest;

import lombok.Getter;
import lombok.ToString;
import lombok.AllArgsConstructor;

@Getter
@ToString
@AllArgsConstructor
public class StressTestResult
{
    private final long totalRuns;
    private final int successCount;
    private final int errorCount;
    private final double averageRuntime;
    private final long runtime90Percentile;
    private final long runtime80Percentile;
}