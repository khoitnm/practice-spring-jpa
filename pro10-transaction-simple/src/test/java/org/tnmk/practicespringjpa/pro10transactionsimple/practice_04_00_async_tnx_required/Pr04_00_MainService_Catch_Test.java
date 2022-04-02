package org.tnmk.practicespringjpa.pro10transactionsimple.practice_04_00_async_tnx_required;

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
public class Pr04_00_MainService_Catch_Test extends BaseSpringTest_WithActualDb {
  @Autowired
  private Pr04_00_MainService mainService;

  @Autowired
  private SimpleRepository simpleRepository;

  @AfterEach
  @Transactional
  public void cleanUp() {
    simpleRepository.deleteAll();
  }

  /**
   * This case suppose to have the same assertions of {@link Pr03_02_MainService_Catch_Test}
   * However, it's interestingly different!!!
   * <p/>
   * 1. The main different is `assertExist(item01, false);`
   *    In {@link Pr03_02_MainService_Catch_Test}, it's `assertExist(item01, true);` <br/>
   * 2. This test case actually behaves exactly as {@link Pr04_00_MainService_NoCatch_Test}.
   */
  @Test
  public void when_MainService_saveListAsync_then_wontBeRolledBack() {
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
    assertExist(item01, false);
    assertExist(item03, false);
  }

  private void assertExist(String entityName, boolean expectExist) {
    Optional<SimpleEntity> sampleEntityOptional = simpleRepository.findByName(entityName);
    Assertions.assertEquals(expectExist, sampleEntityOptional.isPresent(), "Expect '" + entityName + "' existence to be " + expectExist);
  }
}
