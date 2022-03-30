package org.tnmk.practicespringjpa.pro10transactionsimple.practice_00_simple_nested_tnx;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.practicespringjpa.pro10transactionsimple.testinfra.BaseSpringTest_WithActualDb;

import java.util.Optional;

public class SimpleServiceMainTest extends BaseSpringTest_WithActualDb {
  @Autowired
  private SimpleService_Main simpleServiceMain;

  @Autowired
  private SimpleRepository simpleRepository;

  @AfterEach
  public void cleanUp() {
    simpleRepository.deleteAll();
  }

  @Test
  public void test_when_everything_is_saved_successfully() {
    // Given
    SimpleEntity toBeSavedInMainMethod = SimpleEntityFactory.constructSampleEntity();
    SimpleEntity toBeSavedInPrivateMethod = SimpleEntityFactory.constructSampleEntity();
    SimpleEntity toBeSavedInNestedService = SimpleEntityFactory.constructSampleEntity();

    // When
    SaveEntitiesResult result = simpleServiceMain.saveEntities(toBeSavedInMainMethod, toBeSavedInPrivateMethod, toBeSavedInNestedService);

    // Then
    assertExist(result.getToBeSavedInMainMethod(), true);
    assertExist(result.getToBeSavedInPrivateMethod(), true);
    assertExist(result.getToBeSavedInNestedService(), true);

  }

  private void assertExist(SimpleEntity simpleEntity, boolean expectExist) {
    Optional<SimpleEntity> sampleEntityOptional = simpleRepository.findById(simpleEntity.getId());
    Assert.assertEquals(expectExist, sampleEntityOptional.isPresent());
  }
}
