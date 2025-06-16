package org.tnmk.practice_spring_jpa.pro07_multi_tenant_row_level.common;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.tnmk.practice_spring_jpa.pro07_multi_tenant_row_level.common.test_target.DbUserService;
import org.tnmk.practice_spring_jpa.pro07_multi_tenant_row_level.support_for_testing.StressTestHelper;
import org.tnmk.practice_spring_jpa.pro07_multi_tenant_row_level.support_for_testing.StressTestResult;

import javax.sql.DataSource;
import java.sql.Connection;

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
        StressTestResult result = StressTestHelper.run(5, 2, new StressTestHelper.ThrowingBiConsumer<Integer, Integer>() {
            @Override
            public void accept(Integer threadIndex, Integer loopIndex) throws Exception {
                String dbUsername = "stressDbUserC-%s-%s".formatted(threadIndex, loopIndex);
                try (Connection connection = dataSource.getConnection();) {
                    dbUserService.createDbUser(connection, dbUsername);
                }
            }
        });

        Assertions.assertThat(result.getErrorCount()).isEqualTo(0);
    }

}
