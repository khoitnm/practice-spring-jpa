package org.tnmk.practicespringjpa.pro10transactionsimple.practice_00_simple_nested_tnx;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.practicespringjpa.pro10transactionsimple.testinfra.BaseSpringTest_WithActualDb;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

public class SimpleServiceMainTest extends BaseSpringTest_WithActualDb {
  @Autowired
  private SimpleService_Main simpleServiceMain;

  @Autowired
  private SimpleRepository simpleRepository;

  @AfterEach
  @Transactional
  public void cleanUp() {
    simpleRepository.deleteAll();
  }

  @Test
  public void test_everything_is_saved_successfully() {
    // Given
    SimpleEntity toBeSavedInMainMethod = new SimpleEntity("Name" + UUID.randomUUID());
    SimpleEntity toBeSavedInPrivateMethod = new SimpleEntity("Name" + UUID.randomUUID());
    SimpleEntity toBeSavedInNestedService = new SimpleEntity("Name" + UUID.randomUUID());

    // When
    SaveEntitiesResult result = simpleServiceMain.saveEntities(toBeSavedInMainMethod, toBeSavedInPrivateMethod, toBeSavedInNestedService);

    // Then
    assertExist(result.getToBeSavedInMainMethod(), true);
    assertExist(result.getToBeSavedInPrivateMethod(), true);
    assertExist(result.getToBeSavedInNestedService(), true);
  }

  @ParameterizedTest
  @CsvSource(value = {
      //entityInMainMethod  ,entityInPrivateMethod ,entityInNestedService
      "Name01               ,Name02                ,",        // entityInNestedService is null: it will cause roll back.
      "Name01               ,                      ,Name03",  // entityInPrivateMethod is null: it will cause roll back.
      "                     ,Name02                ,Name03"   // entityInMainMethod is null: obviously no thing will be saved.
  })
  public void when_nestedService_error__then__everything_is_rolled_back(
      String entityInMainMethod,
      String entityInPrivateMethod,
      String entityInNestedService) {
    // Given
    SimpleEntity toBeSavedInMainMethod = new SimpleEntity(entityInMainMethod);
    SimpleEntity toBeSavedInPrivateMethod = new SimpleEntity(entityInPrivateMethod);
    SimpleEntity toBeSavedInNestedService = new SimpleEntity(entityInNestedService); // this will cause error

    try {
      // When
      simpleServiceMain.saveEntities(toBeSavedInMainMethod, toBeSavedInPrivateMethod, toBeSavedInNestedService);
    } catch (IllegalArgumentException ex) {
      // Then
      // expect exception happens here:
      Assertions.assertEquals(Collections.emptyList(), simpleRepository.findAll());
    }
  }

  private void assertExist(SimpleEntity simpleEntity, boolean expectExist) {
    Optional<SimpleEntity> sampleEntityOptional = simpleRepository.findById(simpleEntity.getId());
    Assertions.assertEquals(expectExist, sampleEntityOptional.isPresent());
  }
}
