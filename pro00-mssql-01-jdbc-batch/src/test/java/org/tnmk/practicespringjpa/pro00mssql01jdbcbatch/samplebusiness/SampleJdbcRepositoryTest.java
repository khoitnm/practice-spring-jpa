package org.tnmk.practicespringjpa.pro00mssql01jdbcbatch.samplebusiness;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.practicespringjpa.pro00mssql01jdbcbatch.entity.SampleEntity;
import org.tnmk.practicespringjpa.pro00mssql01jdbcbatch.repository.SampleRepository;
import org.tnmk.practicespringjpa.pro00mssql01jdbcbatch.repository.SimpleJdbcRepository;
import org.tnmk.practicespringjpa.pro00mssql01jdbcbatch.testinfra.BaseSpringTest_WithActualDb;

import java.util.UUID;

@Slf4j
public class SampleJdbcRepositoryTest extends BaseSpringTest_WithActualDb {
  @Autowired
  private SimpleJdbcRepository simpleJdbcRepository;

  @Autowired
  private SampleRepository sampleRepository;

  @Test
  public void test() {
    String entityName = "SimpleJdbcRepository_Test_" + UUID.randomUUID();
    long entityId = simpleJdbcRepository.insert(entityName);

    SampleEntity sampleEntity = sampleRepository.findById(entityId).get();
    log.info("Inserted data inside DB: " + sampleEntity);
    Assertions.assertEquals(sampleEntity.getName(), entityName);
  }
}
