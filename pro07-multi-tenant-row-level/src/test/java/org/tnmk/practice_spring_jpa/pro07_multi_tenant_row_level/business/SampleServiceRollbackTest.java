package org.tnmk.practice_spring_jpa.pro07_multi_tenant_row_level.business;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.tnmk.practice_spring_jpa.pro07_multi_tenant_row_level.common.security.SecurityContext;
import org.tnmk.practice_spring_jpa.pro07_multi_tenant_row_level.support_for_testing.SimpleJdbcHelper;

import java.util.Map;
import java.util.Optional;

@Slf4j
@SpringBootTest
class SampleServiceRollbackTest {

    @Autowired
    private SampleService sampleService;

    @Autowired
    private SimpleJdbcHelper simpleJdbcHelper;

    @Test
    void testRollbackTransaction() {
        // GIVEN
        String tenantId = "rollbackTenant";
        SecurityContext.setTenantId(tenantId);

        String entityName = "Rollback Entity";

        // WHEN
        Assertions.assertThrows(RuntimeException.class, () -> {
            sampleService.errorRollbackTrans(entityName);
        });

        // THEN
        // tenant USER won't be rolled back.
        String sql = "SELECT * FROM sys.database_principals WHERE name = ?";
        Optional<Map<String, Object>> foundUser = simpleJdbcHelper.findOne(new ColumnMapRowMapper(), sql, tenantId);
        Assertions.assertTrue(foundUser.isPresent(),
                "The created tenant should be remain and won't be rolled back along with the transaction in business service.");

        // The created entity should be rolled back.
        String entitySql = "SELECT * FROM sample_entity WHERE name = ?";
        Optional<Map<String, Object>> foundEntity = simpleJdbcHelper.findOne(new ColumnMapRowMapper(), entitySql, entityName);
        Assertions.assertFalse(foundEntity.isPresent(),
                "The created entity should be rolled back along with the transaction in business service.");
    }

}
