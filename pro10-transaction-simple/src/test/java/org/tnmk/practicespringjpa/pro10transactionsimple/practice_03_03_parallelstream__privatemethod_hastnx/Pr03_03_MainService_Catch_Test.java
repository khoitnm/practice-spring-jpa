package org.tnmk.practicespringjpa.pro10transactionsimple.practice_03_03_parallelstream__privatemethod_hastnx;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.annotation.Propagation;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SimpleEntity;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SimpleRepository;
import org.tnmk.practicespringjpa.pro10transactionsimple.testinfra.BaseSpringTest_WithActualDb;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
public class Pr03_03_MainService_Catch_Test extends BaseSpringTest_WithActualDb {
  @Autowired
  private Pr03_03_MainService_ParallelStreamWithPrivateMethod_WithCatch mainService;

  @Autowired
  private SimpleRepository simpleRepository;

  @AfterEach
  @Transactional
  public void cleanUp() {
    simpleRepository.deleteAll();
  }

  @ParameterizedTest
  @CsvSource({
      //tnxPropagation                                  ,expectUnexpectedRollback ,expectExistBeforeParallel  ,expectExistAfterParallel ,expectExistParallel01  ,expectExistParallel03
      TransactionDefinition.PROPAGATION_REQUIRED + "    ,true                     ,false                      ,false                    ,true                   ,true",
      TransactionDefinition.PROPAGATION_REQUIRES_NEW + ",false                    ,true                       ,true                     ,true                   ,true",

      // This case is very interesting:
      //  With Propagation SUPPORT, it will reuse the transaction from the main logic (you'll see "Is new trans: false" in the log).
      //  It means the main logic, and all of parallel threads are using the same logical transaction.
      //
      //  However, when one of the parallel item failed and rolled back, other threads still can commit and won't be rolled back!!!
      //  So I guess each thread will have it's own saving point?! But it may not be correct because of the next observation.
      //
      //  Another surprise thing is:
      //  the data inside main logic is rolled back even though it's already catch exceptions from child thread.
      //  aren't they have different saving point??? I guess not, then how it actually works???
      TransactionDefinition.PROPAGATION_SUPPORTS + "    ,true                     ,false                      ,false                    ,true                   ,true"
  })
  public void when_MainService_saveInParallel_then_wontBeRolledBack(
      int tnxPropagation,
      boolean expectUnexpectedRollback,
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
    } catch (UnexpectedRollbackException ex) {
      if (!expectUnexpectedRollback) {
        Assertions.fail("We don't expect to get UnexpectedRollbackException, but still get it with transaction propagation " +
            toStringPropagation(tnxPropagation));
      } else {
        log.info("Expect to get UnexpectedRollbackException with propagation " + toStringPropagation(tnxPropagation));
      }
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

  /**
   * @param propagation get from {@link TransactionDefinition} or {@link Propagation}.
   * @return
   */
  private String toStringPropagation(int propagation) {
    return Propagation.values()[propagation].name();
  }
}
