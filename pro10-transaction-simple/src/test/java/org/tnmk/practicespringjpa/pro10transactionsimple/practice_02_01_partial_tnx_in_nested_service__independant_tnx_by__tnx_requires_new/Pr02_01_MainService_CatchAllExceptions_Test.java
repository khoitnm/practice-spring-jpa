package org.tnmk.practicespringjpa.pro10transactionsimple.practice_02_01_partial_tnx_in_nested_service__independant_tnx_by__tnx_requires_new;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SimpleEntity;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SimpleRepository;
import org.tnmk.practicespringjpa.pro10transactionsimple.testinfra.BaseSpringTest_WithActualDb;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

public class Pr02_01_MainService_CatchAllExceptions_Test extends BaseSpringTest_WithActualDb {
  @Autowired
  private Pr02_01_MainService_CatchAllExceptions mainService;

  @Autowired
  private SimpleRepository simpleRepository;

  @AfterEach
  @Transactional
  public void cleanUp() {
    simpleRepository.deleteAll();
  }

  @ParameterizedTest
  @CsvSource(value = {
      //entity_InNestedService_PartialTnx  ,entity_InNestedService_AfterPartialTnx ||expectExist_alwaysSuccessName_InMainService ,expectExist_alwaysSuccessName_InNestedService_PartialTnx ,expectExist_entity_InNestedService_PartialTnx  ,expectExist_entity_InNestedService_AfterPartialTnx
      //-----------------------------------,---------------------------------------||--------------------------------------------,---------------------------------------------------------,-----------------------------------------------,--------------------------------------------------
      "Name01                              ,Name02                                  ,true                                        ,true                                                     ,true                                           ,true",

      // entity_InNestedService_PartialTnx is null, hence cause exception and then the whole partial transaction will be rolled back,
      // it means alwaysSuccessName_InNestedService_PartialTnx will also be rolled back.
      // right after rolling back, partialTnx block will again throw out the exception to the mainService.
      // mainService will catch that exception and won't be rolled back.
      "                                    ,Name02                                  ,true                                       ,false                                                     ,false                                          ,false",

      // Only entity_InNestedService_AfterPartialTnx cause error.
      // But then the exception will be cough by mainBusiness, so mainBusiness won't be rolled back.
      "Name01                              ,                                        ,true                                      ,true                                                      ,true                                           ,false",

      // partialTnx is rolled back and then throw exception to main business (ignore entity_InNestedService_AfterPartialTnx)
      // main business will catch that and commit transaction as normal.
      "                                    ,                                        ,true                                      ,false                                                     ,false                                          ,false",
  })
  public void test_MainService_CatchAllExceptions_saveEntities(
      String entityName_InNestedService_PartialTnx,
      String entityName_InNestedService_AfterPartialTnx,

      boolean expectExistenceOf_alwaysSuccessName_InMainService,
      boolean expectExistenceOf_alwaysSuccessName_InNestedService_PartialTnx,
      boolean expectExistenceOf_entity_InNestedService_PartialTnx,
      boolean expectExistenceOf_entity_InNestedService_AfterPartialTnx
  ) {
    // Given
    String alwaysSuccessName_InMainService = "alwaysSuccess_InMainService_" + UUID.randomUUID();
    String alwaysSuccessName_InNestedService_PartialTnx = "alwaysSuccess_InNestedService_PartialTnx_" + UUID.randomUUID();

    // When
    mainService.saveEntities(
        alwaysSuccessName_InMainService,
        alwaysSuccessName_InNestedService_PartialTnx,
        entityName_InNestedService_PartialTnx,
        entityName_InNestedService_AfterPartialTnx);

    assertExist(alwaysSuccessName_InMainService, expectExistenceOf_alwaysSuccessName_InMainService);
    assertExist(alwaysSuccessName_InNestedService_PartialTnx, expectExistenceOf_alwaysSuccessName_InNestedService_PartialTnx);
    assertExist(entityName_InNestedService_PartialTnx, expectExistenceOf_entity_InNestedService_PartialTnx);
    assertExist(entityName_InNestedService_AfterPartialTnx, expectExistenceOf_entity_InNestedService_AfterPartialTnx);
  }

  private void assertExist(String entityName, boolean expectExist) {
    Optional<SimpleEntity> sampleEntityOptional = simpleRepository.findByName(entityName);
    Assertions.assertEquals(expectExist, sampleEntityOptional.isPresent(), "Expect '"+entityName+"' existence to be "+expectExist);
  }
}
