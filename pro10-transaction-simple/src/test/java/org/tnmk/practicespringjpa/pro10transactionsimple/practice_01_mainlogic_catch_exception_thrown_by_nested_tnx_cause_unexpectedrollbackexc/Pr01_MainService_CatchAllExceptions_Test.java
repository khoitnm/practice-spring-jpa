package org.tnmk.practicespringjpa.pro10transactionsimple.practice_01_mainlogic_catch_exception_thrown_by_nested_tnx_cause_unexpectedrollbackexc;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.UnexpectedRollbackException;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SaveEntitiesResult;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SimpleEntity;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SimpleRepository;
import org.tnmk.practicespringjpa.pro10transactionsimple.testinfra.BaseSpringTest_WithActualDb;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

public class Pr01_MainService_CatchAllExceptions_Test extends BaseSpringTest_WithActualDb {
  @Autowired
  private Pr01_MainService_CatchAllExceptions mainService;

  @Autowired
  private SimpleRepository simpleRepository;

  @AfterEach
  @Transactional
  public void cleanUp() {
    simpleRepository.deleteAll();
  }

  @ParameterizedTest
  @CsvSource(value = {
      //entityInMainMethod  ,entityInPrivateMethod ,entityInNestedService_withoutNestedTnx ,expectSavedInMainMethod, expectSavedInPrivateMethod, expectedSavedInNestedMethod
      "Name01               ,                      ,Name03                                 ,true                   , false                     , true",
      "                     ,Name02                ,Name03                                 ,false                  , true                      , true",
      "Name01               ,Name02                ,                                       ,true                   , true                      , false"

      // The case when entityInNestedService causes error will be covered in the next test case:
      // when_get_UnexpectedRollbackException__then__all_data_including_mainService_will_be_rolled_back()
  })
  public void test_MainService_CatchAllExceptions_saveEntities(
      String entityInMainMethod,
      String entityInPrivateMethod,
      String entityInNestedService_withoutNestedTnx,

      boolean expectSavedInMainMethod,
      boolean expectSavedInPrivateMethod,
      boolean expectedSavedInNestedMethod
  ) {
    // Given
    SimpleEntity toBeSavedInMainMethod = new SimpleEntity(entityInMainMethod);
    SimpleEntity toBeSavedInPrivateMethod = new SimpleEntity(entityInPrivateMethod);
    SimpleEntity toBeSavedInNestedService_withNestedTnx = new SimpleEntity("Nested Transaction Won't Fail");
    SimpleEntity toBeSavedInNestedService_withoutNestedTnx = new SimpleEntity(entityInNestedService_withoutNestedTnx);

    // When
    SaveEntitiesResult result = mainService.saveEntities(toBeSavedInMainMethod, toBeSavedInPrivateMethod,
        toBeSavedInNestedService_withNestedTnx, toBeSavedInNestedService_withoutNestedTnx);

    //    assertExist(result.getAlwaysSuccessInMainMethod(), true);

    if (expectSavedInMainMethod) {
      assertExist(toBeSavedInMainMethod.getName(), true);
    }
    if (expectSavedInPrivateMethod) {
      assertExist(toBeSavedInPrivateMethod.getName(), true);
    }
    if (expectedSavedInNestedMethod) {
      assertExist(toBeSavedInNestedService_withoutNestedTnx.getName(), true);
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
    SimpleEntity toBeSavedInNestedService_withNestedTnx = new SimpleEntity(null);
    SimpleEntity toBeSavedInNestedService_withoutNestedTnx = new SimpleEntity("Nested Service (without Nested Transaction) won't fail.");

    // When
    try {
      mainService.saveEntities(toBeSavedInMainMethod, toBeSavedInPrivateMethod, toBeSavedInNestedService_withNestedTnx,
          toBeSavedInNestedService_withoutNestedTnx);
    } catch (UnexpectedRollbackException ex) {
      Assertions.assertEquals(Collections.emptyList(), simpleRepository.findAll());
    }
  }

  private void assertExist(String entityName, boolean expectExist) {
    Optional<SimpleEntity> sampleEntityOptional = simpleRepository.findByName(entityName);
    Assertions.assertEquals(expectExist, sampleEntityOptional.isPresent());
  }
}
