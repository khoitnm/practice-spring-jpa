package org.tnmk.practice_spring_jpa.pro08_multi_tenant_session_context.common;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.tnmk.practice_spring_jpa.pro08_multi_tenant_session_context.common.multitenant.MultiTenantConnectionProviderImpl;
import org.tnmk.practice_spring_jpa.pro08_multi_tenant_session_context.support_for_testing.stresstest.StressTestHelper;
import org.tnmk.practice_spring_jpa.pro08_multi_tenant_session_context.support_for_testing.stresstest.StressTestResult;

import java.sql.Connection;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class MultiTenantConnectionProviderStressTest {

    @Autowired
    private MultiTenantConnectionProviderImpl multiTenantConnectionProvider;

    @DisplayName("This test will show how MultiTenantConnectionProviderImpl can handle many parallel requests at the same time." +
        "Right now, it's getting connection problem because MS SQL Server cannot handle too many DDL in parallel.")
    @Test
    void stressTest() {
        StressTestResult result = StressTestHelper.run(50, 100, (threadIndex, loopIndex) -> {
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
