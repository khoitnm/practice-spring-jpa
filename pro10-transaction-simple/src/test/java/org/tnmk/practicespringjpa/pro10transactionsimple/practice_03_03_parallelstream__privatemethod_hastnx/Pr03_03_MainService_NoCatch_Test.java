package org.tnmk.practicespringjpa.pro10transactionsimple.practice_03_03_parallelstream__privatemethod_hastnx;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionDefinition;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SimpleEntity;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SimpleRepository;
import org.tnmk.practicespringjpa.pro10transactionsimple.testinfra.BaseSpringTest_WithActualDb;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
public class Pr03_03_MainService_NoCatch_Test extends BaseSpringTest_WithActualDb {
  @Autowired
  private Pr03_03_MainService_ParallelStreamWithPrivateMethod_NoCatch mainService;

  @Autowired
  private SimpleRepository simpleRepository;

  @AfterEach
  @Transactional
  public void cleanUp() {
    simpleRepository.deleteAll();
  }

  @ParameterizedTest
  @CsvSource({
      //tnxPropagation                                  ,expectExistBeforeParallel  ,expectExistAfterParallel ,expectExistParallel01  ,expectExistParallel03
      TransactionDefinition.PROPAGATION_REQUIRED + "    ,false                      ,false                    ,true                   ,true",
      TransactionDefinition.PROPAGATION_REQUIRES_NEW + ",false                      ,false                    ,true                   ,true",
      TransactionDefinition.PROPAGATION_SUPPORTS + "    ,false                      ,false                    ,true                   ,true"
  })
  public void when_MainService_saveInParallel_then_wontBeRolledBack(
      int tnxPropagation,
      boolean expectExistBeforeParallel,
      boolean expectExistAfterParallel,
      boolean expectExistParallel01,
      boolean expectExistParallel03
  ) {
    // GIVEN --------------------------------------------------

    // Items in mainService
    String alwaysSuccessName_InMainService_BeforeParallel = "alwaysSuccessName_InMainService_BeforeParallel" + UUID.randomUUID();
    String alwaysSuccessName_InMainService_AfterParallel = "alwaysSuccessName_InMainService_AfterParallel" + UUID.randomUUID();

    // Parallel items
    String item01 = "Item01";

    // This null value will cause exception.
    // Even though it run in parallel threads, but parallelStream still waiting for join those parallel threads,
    // So when an item get error, the mainService will get that error and be rolled back.
    // However, other parallel threads is running without transaction, so they will be committed and won't be rolled backed.
    String item02 = null;

    String item03 = "Item03";
    List<String> entityNames = Arrays.asList(item01, item02, item03);

    // WHEN --------------------------------------------------
    try {
      mainService.saveEntities_WithPrivateMethod_NoCatchEx(
          alwaysSuccessName_InMainService_BeforeParallel,
          alwaysSuccessName_InMainService_AfterParallel,
          entityNames,
          tnxPropagation);
    } catch (IllegalArgumentException ex) {
      log.info(ex.getMessage());
    }

    // THEN --------------------------------------------------
    assertExist(alwaysSuccessName_InMainService_BeforeParallel, expectExistBeforeParallel);
    assertExist(alwaysSuccessName_InMainService_AfterParallel, expectExistAfterParallel);

    assertExist(item01, expectExistParallel01);
    assertExist(item03, expectExistParallel03);
  }

  private void assertExist(String entityName, boolean expectExist) {
    Optional<SimpleEntity> sampleEntityOptional = simpleRepository.findByName(entityName);
    Assertions.assertEquals(expectExist, sampleEntityOptional.isPresent(), "Expect '" + entityName + "' existence to be " + expectExist);
  }
}
