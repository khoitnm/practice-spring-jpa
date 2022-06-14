package org.tnmk.practicespringjpa.pro00mssql01jdbcbatch.sample;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.practicespringjpa.pro00mssql01jdbcbatch.testinfra.BaseSpringTest_WithActual_MsSqlServer;

import java.util.Optional;

public class SampleBusinessTest extends BaseSpringTest_WithActual_MsSqlServer {
  @Autowired
  private SampleRepository sampleRepository;

  @Test
  public void test_insertOneEntity_successfully() {
    SampleEntity sampleEntity = SampleEntityFactory.constructSampleEntity();
    SampleEntity savedSampleEntity = sampleRepository.save(sampleEntity);

    Optional<SampleEntity> sampleEntityOptional = sampleRepository.findById(savedSampleEntity.getId());
    Assert.assertTrue(sampleEntityOptional.isPresent());
  }
}
