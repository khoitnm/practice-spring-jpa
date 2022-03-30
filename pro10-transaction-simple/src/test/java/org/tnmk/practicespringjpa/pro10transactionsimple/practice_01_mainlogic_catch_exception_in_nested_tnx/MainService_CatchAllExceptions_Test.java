package org.tnmk.practicespringjpa.pro10transactionsimple.practice_01_mainlogic_catch_exception_in_nested_tnx;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SimpleEntity;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SimpleRepository;
import org.tnmk.practicespringjpa.pro10transactionsimple.practice_00_simple_nested_tnx.SaveEntitiesResult;
import org.tnmk.practicespringjpa.pro10transactionsimple.testinfra.BaseSpringTest_WithActualDb;

import javax.transaction.Transactional;
import java.util.Optional;

public class MainService_CatchAllExceptions_Test extends BaseSpringTest_WithActualDb {
  @Autowired
  private MainService_CatchAllExceptions mainService;

  @Autowired
  private SimpleRepository simpleRepository;

  @AfterEach
  @Transactional
  public void cleanUp() {
    simpleRepository.deleteAll();
  }

  @ParameterizedTest
  @CsvSource(value = {
      //entityInMainMethod  ,entityInPrivateMethod ,entityInNestedService ,expectSavedInMainMethod, expectSavedInPrivateMethod, expectedSavedInNestedMethod
      "Name01               ,Name02                ,                      ,true                   , true                      , false",
      "Name01               ,                      ,Name03                ,true                   , false                     , true",
      "                     ,Name02                ,Name03                ,false                  , true                      , true"
  })
  public void test_MainService_CatchAllExceptions_saveEntities(
      String entityInMainMethod,
      String entityInPrivateMethod,
      String entityInNestedService,

      boolean expectSavedInMainMethod,
      boolean expectSavedInPrivateMethod,
      boolean expectedSavedInNestedMethod
  ) {
    // Given
    SimpleEntity toBeSavedInMainMethod = new SimpleEntity(entityInMainMethod);
    SimpleEntity toBeSavedInPrivateMethod = new SimpleEntity(entityInPrivateMethod);
    SimpleEntity toBeSavedInNestedService = new SimpleEntity(entityInNestedService); // this will cause error

    // When
    SaveEntitiesResult result = mainService.saveEntities(toBeSavedInMainMethod, toBeSavedInPrivateMethod, toBeSavedInNestedService);

    if (expectSavedInMainMethod) {
      assertExist(result.getToBeSavedInMainMethod(), true);
    }
    if (expectSavedInPrivateMethod) {
      assertExist(result.getToBeSavedInPrivateMethod(), true);
    }
    if (expectedSavedInNestedMethod) {
      assertExist(result.getToBeSavedInNestedService(), true);
    }
  }

  private void assertExist(SimpleEntity simpleEntity, boolean expectExist) {
    Optional<SimpleEntity> sampleEntityOptional = simpleRepository.findById(simpleEntity.getId());
    Assertions.assertEquals(expectExist, sampleEntityOptional.isPresent());
  }
}
