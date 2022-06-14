package org.tnmk.practicespringjpa.pro00mssql01jdbcbatch.sample.batch;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.practicespringjpa.pro00mssql01jdbcbatch.sample.SampleEntity;
import org.tnmk.practicespringjpa.pro00mssql01jdbcbatch.sample.SampleRepository;
import org.tnmk.practicespringjpa.pro00mssql01jdbcbatch.testinfra.BaseSpringTest_WithActual_MsSqlServer;
import org.tnmk.practicespringjpa.pro00mssql01jdbcbatch.testinfra.BaseSpringTest_WithActual_MySql;

import java.util.Arrays;
import java.util.List;

/**
 * As mentioned in {@link SampleBatchJdbcRepository_NotWorkFor_MsSqlServer},
 * This test case can work with MySQL (requires MySQL dependency in pom.xml), but not with MS SQL Server.
 * So I'm disabling it for now.
 */
@Disabled
@Slf4j
public class SampleBatchJdbcRepositoryTest extends BaseSpringTest_WithActual_MySql {
  @Autowired
  private SampleBatchJdbcRepository_NotWorkFor_MsSqlServer batchJdbcRepository;

  @Autowired
  private SampleRepository sampleRepository;

  @Test
  public void test_insertManyEntities_successfully() {
    List<CreateEntityRequest> requests = Arrays.asList(request(2), request(1));
    List<CreateEntityResult> results = batchJdbcRepository.insert(requests);

    for (CreateEntityResult result : results) {
      CreateEntityRequest request = result.getRequest();
      SampleEntity sampleEntity = sampleRepository.findByEntityCode(request.getEntityCode()).get();
      log.info("Inserted data inside DB: " + sampleEntity);
      Assertions.assertEquals(sampleEntity.getName(), request.getName());
    }
  }

  private CreateEntityRequest request(int manualIndex) {
    return new CreateEntityRequest("N_" + manualIndex, "C_" + manualIndex);
  }
}
