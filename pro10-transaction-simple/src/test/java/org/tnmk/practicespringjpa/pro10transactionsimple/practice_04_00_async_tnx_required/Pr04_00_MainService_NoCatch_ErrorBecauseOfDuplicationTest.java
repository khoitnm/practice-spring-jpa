package org.tnmk.practicespringjpa.pro10transactionsimple.practice_04_00_async_tnx_required;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SimpleEntity;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SimpleRepository;
import org.tnmk.practicespringjpa.pro10transactionsimple.practice_03_02_parallelstream__nestedservice_hastnx.Pr03_02_MainService_NoCatch_Test;
import org.tnmk.practicespringjpa.pro10transactionsimple.testinfra.BaseSpringTest_WithActualDb;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletionException;

@Slf4j
public class Pr04_00_MainService_NoCatch_ErrorBecauseOfDuplicationTest extends BaseSpringTest_WithActualDb {
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
   * As expected, this test case has the same assertions of {@link Pr03_02_MainService_NoCatch_Test}
   * Just a small difference:<br/>
   * {@link Pr03_02_MainService_NoCatch_Test} will get {@link IllegalArgumentException}
   *    , while this will get {@link CompletionException} with root cause is {@link IllegalArgumentException}
   */
  @Test
  public void when_MainService_saveListAsync_then_wontBeRolledBack() {
    // GIVEN --------------------------------------------------

    // Items in mainService
    String alwaysSuccessName_InMainService_BeforeParallel = "alwaysSuccessName_InMainService_BeforeParallel" + UUID.randomUUID();
    String alwaysSuccessName_InMainService_AfterParallel = "alwaysSuccessName_InMainService_AfterParallel" + UUID.randomUUID();

    // Parallel items
    String item01 = "Item01";

    // This duplicate (with item01) value will cause exception.
    // Even though it run in parallel threads, but parallelStream still waiting for join those parallel threads,
    // So when an item get error, the mainService will get that error and be rolled back.
    // However, other parallel threads is running without transaction, so they will be committed and won't be rolled backed.
    String item02 = "Item01";

    String item03 = "Item03";
    List<String> entityNames = Arrays.asList(item01, item02, item03);

    // WHEN --------------------------------------------------
    try {
      mainService.saveEntities_NoCatchEx(
          alwaysSuccessName_InMainService_BeforeParallel,
          alwaysSuccessName_InMainService_AfterParallel,
          entityNames);
    } catch (CompletionException ex) {
      log.info("Error when saveEntities_NoCatchEx(): "+ex.getMessage(), ex);
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
