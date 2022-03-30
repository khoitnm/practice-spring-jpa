package org.tnmk.practicespringjpa.pro10transactionsimple.practice_00_simple;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.practicespringjpa.pro10transactionsimple.testinfra.BaseSpringTest_WithActualDb;

import java.util.Optional;

public class SimpleServiceTest extends BaseSpringTest_WithActualDb {
  @Autowired
  private SimpleService simpleService;

  @Test
  public void test() {
    SimpleEntity simpleEntity = SimpleEntityFactory.constructSampleEntity();
    SimpleEntity savedSimpleEntity = simpleService.create(simpleEntity);

    Optional<SimpleEntity> sampleEntityOptional = simpleService.findById(savedSimpleEntity.getId());
    Assert.assertTrue(sampleEntityOptional.isPresent());
  }
}
