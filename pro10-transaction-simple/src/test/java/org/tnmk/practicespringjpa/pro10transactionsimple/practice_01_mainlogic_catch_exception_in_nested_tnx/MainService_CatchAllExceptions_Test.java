package org.tnmk.practicespringjpa.pro10transactionsimple.practice_01_mainlogic_catch_exception_in_nested_tnx;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.UnexpectedRollbackException;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SimpleEntity;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SimpleRepository;
import org.tnmk.practicespringjpa.pro10transactionsimple.practice_00_simple_nested_tnx.SaveEntitiesResult;
import org.tnmk.practicespringjpa.pro10transactionsimple.testinfra.BaseSpringTest_WithActualDb;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

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
    SimpleEntity toBeSavedInNestedService = new SimpleEntity(entityInNestedService);

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

  @Test
  public void when_get_UnexpectedRollbackException__then__all_data_including_mainService_will_be_rolled_back() {
    // Given
    SimpleEntity toBeSavedInMainMethod = new SimpleEntity("Name" + UUID.randomUUID());
    SimpleEntity toBeSavedInPrivateMethod = new SimpleEntity("Name" + UUID.randomUUID());

    // this will cause UnexpectedRollbackException because the transaction in NestedBusiness will be rolled back,
    // but the transaction in MainBusiness won't aware of it (because of catching).
    // so UnexpectedRollbackException will be thrown.
    SimpleEntity toBeSavedInNestedService = new SimpleEntity(null);

    // When
    try {
      mainService.saveEntities(toBeSavedInMainMethod, toBeSavedInPrivateMethod, toBeSavedInNestedService);
    } catch (UnexpectedRollbackException ex) {
      Assertions.assertEquals(Collections.emptyList(), simpleRepository.findAll());
    }
  }

  private void assertExist(SimpleEntity simpleEntity, boolean expectExist) {
    Optional<SimpleEntity> sampleEntityOptional = simpleRepository.findById(simpleEntity.getId());
    Assertions.assertEquals(expectExist, sampleEntityOptional.isPresent());
  }
}
