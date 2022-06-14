package org.tnmk.practicespringjpa.pro00mssql01jdbcbatch.sample;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.practicespringjpa.pro00mssql01jdbcbatch.testinfra.BaseSpringTest_WithActual_MsSqlServer;

import java.util.UUID;

@Slf4j
public class SampleJdbcRepositoryTest extends BaseSpringTest_WithActual_MsSqlServer {
  @Autowired
  private SampleJdbcRepository sampleJdbcRepository;

  @Autowired
  private SampleRepository sampleRepository;

  @Test
  public void test_insertOneEntity_successfully() {
    String entityName = "SimpleJdbcRepository_Test_" + UUID.randomUUID();
    String uniqueCode = "C" + UUID.randomUUID();
    long entityId = sampleJdbcRepository.insert(entityName, uniqueCode);

    SampleEntity sampleEntity = sampleRepository.findById(entityId).get();
    log.info("Inserted data inside DB: " + sampleEntity);
    Assertions.assertEquals(sampleEntity.getName(), entityName);
  }
}
