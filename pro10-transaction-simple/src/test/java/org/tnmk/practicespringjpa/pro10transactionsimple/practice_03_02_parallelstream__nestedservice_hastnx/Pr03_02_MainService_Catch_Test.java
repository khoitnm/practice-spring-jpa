package org.tnmk.practicespringjpa.pro10transactionsimple.practice_03_02_parallelstream__nestedservice_hastnx;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.UnexpectedRollbackException;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SimpleEntity;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SimpleRepository;
import org.tnmk.practicespringjpa.pro10transactionsimple.testinfra.BaseSpringTest_WithActualDb;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
public class Pr03_02_MainService_Catch_Test extends BaseSpringTest_WithActualDb {
  @Autowired
  private Pr03_02_MainService_ParallelStream mainService;

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
    // And it will be rolled back in the transaction of that thread.
    //
    // The mainService will try to catch that.
    // And because of propagation type REQUIRED, the main transaction is forced to be rolled back
    // when the child transaction is rolled back.

    // Other items in parallel threads also have propagation transaction REQUIRED,
    // means they are running on different transaction
    // so won't be rolled back.
    String item02 = null;

    String item03 = "Item03";
    List<String> entityNames = Arrays.asList(item01, item02, item03);

    // WHEN --------------------------------------------------
    try {
      mainService.saveEntities_CatchEx(
          alwaysSuccessName_InMainService_BeforeParallel,
          alwaysSuccessName_InMainService_AfterParallel,
          entityNames);
    } catch (UnexpectedRollbackException ex) {
      log.info(ex.getMessage(), ex);
    }

    // THEN --------------------------------------------------
    assertExist(alwaysSuccessName_InMainService_BeforeParallel, false);
    assertExist(alwaysSuccessName_InMainService_AfterParallel, false);
    assertExist(item01, true);
    assertExist(item03, true);
  }

  private void assertExist(String entityName, boolean expectExist) {
    Optional<SimpleEntity> sampleEntityOptional = simpleRepository.findByName(entityName);
    Assertions.assertEquals(expectExist, sampleEntityOptional.isPresent(), "Expect '" + entityName + "' existence to be " + expectExist);
  }
}