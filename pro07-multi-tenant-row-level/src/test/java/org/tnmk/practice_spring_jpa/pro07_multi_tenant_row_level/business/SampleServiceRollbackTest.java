package org.tnmk.practice_spring_jpa.pro07_multi_tenant_row_level.business;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.tnmk.practice_spring_jpa.pro07_multi_tenant_row_level.common.security.SecurityContext;
import org.tnmk.practice_spring_jpa.pro07_multi_tenant_row_level.support_for_testing.SimpleJdbcRepositoryHelper;

import java.util.Map;
import java.util.Optional;

@Slf4j
@SpringBootTest
class SampleServiceRollbackTest
{

  @Autowired
  private SampleService sampleService;

  @Autowired
  private SimpleJdbcRepositoryHelper simpleJdbcRepositoryHelper;

  @Test
  void testRollbackTransaction()
  {
    String tenantId = "rollbackTenant";
    SecurityContext.setTenantId(tenantId);

    Assertions.assertThrows(RuntimeException.class, () -> {
      sampleService.errorRollbackTrans();
    });

    String sql = """
        SELECT * FROM sys.database_principals WHERE name = ?
        """;
    ColumnMapRowMapper rowMapper = new ColumnMapRowMapper();
    Optional<Map<String, Object>> foundUser = simpleJdbcRepositoryHelper.findOne(rowMapper, sql, tenantId);
    Assertions.assertTrue(foundUser.isPresent(),
        "The tenant should be created, and won't be rolled back along with the transaction in business service.");
  }

}
