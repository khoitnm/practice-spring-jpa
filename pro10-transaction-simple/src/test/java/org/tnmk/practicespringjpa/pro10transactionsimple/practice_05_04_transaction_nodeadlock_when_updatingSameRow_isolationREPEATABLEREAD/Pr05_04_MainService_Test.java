package org.tnmk.practicespringjpa.pro10transactionsimple.practice_05_04_transaction_nodeadlock_when_updatingSameRow_isolationREPEATABLEREAD;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SimpleEntity;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SimpleRepository;
import org.tnmk.practicespringjpa.pro10transactionsimple.practice_05_03_transaction_nodeadlock_when_savingSameEntity_with_isolationREPEATREAD.Pr05_03_MainService;
import org.tnmk.practicespringjpa.pro10transactionsimple.testinfra.BaseSpringTest_WithActualDb;

import java.time.ZonedDateTime;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Slf4j
public class Pr05_04_MainService_Test extends BaseSpringTest_WithActualDb {
  @Autowired
  private Pr05_04_MainService service;

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

    Pr05_04_MainService.Result result = service.slowFirst_fastLater(slowName, fastName, slowRuntime, delayFastService);

    SimpleEntity simpleEntity = simpleRepository.findById(result.getSimpleEntityId()).get();

    log.info("startTime:\t{}", startTime);
    log.info("simpleEntity: {}", simpleEntity);
    log.info("slow transaction finish: {}", result.getSlowFinishDateTime());
    log.info("fast transaction finish: {}", result.getFastFinishDateTime());

    // The interesting thing is: 2nd transaction is still blocked by 1 transaction,
    // Hence it cannot be committed before transaction 1.
    Assertions.assertTrue(result.getSlowFinishDateTime().isBefore(result.getFastFinishDateTime()));

  }
}
