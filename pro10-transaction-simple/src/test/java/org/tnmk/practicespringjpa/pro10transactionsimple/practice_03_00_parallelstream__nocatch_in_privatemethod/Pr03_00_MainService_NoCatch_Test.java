package org.tnmk.practicespringjpa.pro10transactionsimple.practice_03_00_parallelstream__nocatch_in_privatemethod;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SimpleEntity;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SimpleRepository;
import org.tnmk.practicespringjpa.pro10transactionsimple.practice_03_01_parallelstream__nestedservice_notnx.Pr03_01_MainService_ParallelStream;
import org.tnmk.practicespringjpa.pro10transactionsimple.testinfra.BaseSpringTest_WithActualDb;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
public class Pr03_00_MainService_NoCatch_Test extends BaseSpringTest_WithActualDb {
  @Autowired
  private Pr03_00_MainService_ParallelStreamWithPrivateMethod_NoCatch mainService;

  @Autowired
  private SimpleRepository simpleRepository;

  @AfterEach
  @Transactional
  public void cleanUp() {
    simpleRepository.deleteAll();
  }

  @Test
  public void when_MainService_saveInParallel_then_wontBeRolledBack() {
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
          entityNames);
    } catch (IllegalArgumentException ex) {
      log.info(ex.getMessage());
    }

    // THEN --------------------------------------------------
    // This item doesn't exist because it is rolled back.
    assertExist(alwaysSuccessName_InMainService_BeforeParallel, false);

    // This item doesn't exist because it has never been saved.
    assertExist(alwaysSuccessName_InMainService_AfterParallel, false);

    assertExist(item01, true);
    assertExist(item03, true);
  }

  private void assertExist(String entityName, boolean expectExist) {
    Optional<SimpleEntity> sampleEntityOptional = simpleRepository.findByName(entityName);
    Assertions.assertEquals(expectExist, sampleEntityOptional.isPresent(), "Expect '" + entityName + "' existence to be " + expectExist);
  }
}
