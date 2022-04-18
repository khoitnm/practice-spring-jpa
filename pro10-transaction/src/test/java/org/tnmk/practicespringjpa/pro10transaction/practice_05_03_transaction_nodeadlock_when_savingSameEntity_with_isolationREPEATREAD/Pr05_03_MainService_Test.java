package org.tnmk.practicespringjpa.pro10transaction.practice_05_03_transaction_nodeadlock_when_savingSameEntity_with_isolationREPEATREAD;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.practicespringjpa.pro10transaction.common.SimpleEntity;
import org.tnmk.practicespringjpa.pro10transaction.common.SimpleRepository;
import org.tnmk.practicespringjpa.pro10transaction.testinfra.BaseSpringTest_WithActualDb;

import java.time.ZonedDateTime;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Slf4j
public class Pr05_03_MainService_Test extends BaseSpringTest_WithActualDb {
  @Autowired
  private Pr05_03_MainService service;

  @Autowired
  private SimpleRepository simpleRepository;

  @Test
  public void test_startSlowBeforeFast() throws ExecutionException, InterruptedException {
    ZonedDateTime startTime = ZonedDateTime.now();

    log.info("Timezone: " + TimeZone.getDefault());

    String slowName = "Slow_" + UUID.randomUUID();
    String fastName = "Fast_" + UUID.randomUUID();
    int slowRuntime = 5000;
    int delayFastService = 1000;

    Pr05_03_MainService.Result result = service.slowFirst_fastLater(slowName, fastName, slowRuntime, delayFastService);

    SimpleEntity simpleEntity = simpleRepository.findById(result.getSimpleEntityId()).get();

    log.info("startTime:\t{}", startTime);
    log.info("simpleEntity: {}", simpleEntity);
    log.info("slow transaction finish: {}", result.getSlowFinishDateTime());
    log.info("fast transaction finish: {}", result.getFastFinishDateTime());

    // This case is very interesting with Isolation.REPEATABLE_READ (less strict than SERIALIZABLE):
    // When we use SimpleRepository.save() (in both SlowService & FastService), it actually will:
    //  - Step 1: select item first
    //  - Step 2: update item later
    //  The thing is, inside the transaction, it just select item first.
    //  And only when the transaction is really ready to be committed, it will execute updating item later (step 2).
    //  It means the other transaction won't be blocked by the previous transaction.
    //
    // This case is totally different from Pr05_04 in which "update" statement is executed in the middle of the transaction,
    // which in turn block other transactions running in parallel.
    Assertions.assertTrue(result.getFastFinishDateTime().isBefore(result.getSlowFinishDateTime()));

  }
}
