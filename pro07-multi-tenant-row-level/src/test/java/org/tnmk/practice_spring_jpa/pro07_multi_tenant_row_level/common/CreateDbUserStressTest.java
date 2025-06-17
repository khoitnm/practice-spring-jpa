package org.tnmk.practice_spring_jpa.pro07_multi_tenant_row_level.common;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.tnmk.practice_spring_jpa.pro07_multi_tenant_row_level.common.test_target.DbUserService;
import org.tnmk.practice_spring_jpa.pro07_multi_tenant_row_level.support_for_testing.stresstest.StressTestHelper;
import org.tnmk.practice_spring_jpa.pro07_multi_tenant_row_level.support_for_testing.stresstest.StressTestResult;

import javax.sql.DataSource;
import java.sql.Connection;

@Slf4j
// This test doesn't care about other tables in the DB, hence no need to care about Flyway script.
@SpringBootTest(properties = {"spring.flyway.enabled=false"})
class CreateDbUserStressTest {

    @Autowired
    DataSource dataSource;

    @Autowired
    private DbUserService dbUserService;

    @Test
    void stressTest() {
        StressTestResult result = StressTestHelper.run(5, 4, (threadIndex, loopIndex) -> {
            String dbUsername = "stressDbUser-%s-%s".formatted(threadIndex, loopIndex);
            try (Connection connection = dataSource.getConnection();) {
                dbUserService.createDbUser(connection, dbUsername);
            }
        });

        Assertions.assertThat(result.getErrorCount()).isEqualTo(0);
    }

}
