package org.tnmk.practice_spring_jpa.pro07_multi_tenant_row_level.common;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.tnmk.practice_spring_jpa.pro07_multi_tenant_row_level.common.multitenant.MultiTenantConnectionProviderImpl;
import org.tnmk.practice_spring_jpa.pro07_multi_tenant_row_level.support_for_testing.stresstest.StressTestHelper;
import org.tnmk.practice_spring_jpa.pro07_multi_tenant_row_level.support_for_testing.stresstest.StressTestResult;

import java.sql.Connection;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class MultiTenantConnectionProviderStressTest {

    public static final int SLOW_TENANT_THRESHOLD_MILLS = 200;
    @Autowired
    private MultiTenantConnectionProviderImpl multiTenantConnectionProvider;

    @DisplayName("This test will show how MultiTenantConnectionProviderImpl can handle many parallel requests at the same time." +
        "Right now, it's getting connection problem because MS SQL Server cannot handle too many DDL in parallel.")
    @Test
    void stressTest() {
        StressTestResult result = StressTestHelper.run(30, 1000, (threadIndex, loopIndex) -> {
            String tenantId = "stressTenant-%s-%s".formatted(threadIndex, loopIndex);
            try (Connection connection = multiTenantConnectionProvider.getConnection(tenantId);) {
                /**
                 * Just closing connection alone is not enough, we need to call `releaseConnection` to REVERT the user context (set by `EXECUTED AS...`) from the connection.
                 * If we don't do that, it will get error for the next thread (please view more in {@link MultiTenantConnectionProviderImpl#releaseAnyConnection(Connection)}.
                 * Question: what if business level get some error, can it REVERT the connection?
                 */
                multiTenantConnectionProvider.releaseConnection(tenantId, connection);
            }
        });
        assertThat(result.getErrorCount()).as("There should be no error when getting connection for tenant.").isEqualTo(0);
    }

}
