package org.tnmk.practicespringjpa.pro10transactionsimple.practice_05_01_transaction_deadlock;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.CannotAcquireLockException;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SimpleRepository;
import org.tnmk.practicespringjpa.pro10transactionsimple.testinfra.BaseSpringTest_WithActualDb;

import java.time.ZonedDateTime;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;

@Slf4j
public class Pr05_01_MainService_Test extends BaseSpringTest_WithActualDb {
  @Autowired
  private Pr05_01_MainService service;

  @Autowired
  private SimpleRepository simpleRepository;

  @Test
  public void test_startSlowBeforeFast() throws ExecutionException, InterruptedException {
    String slowName = "Slow_" + UUID.randomUUID();
    String fastName = "Fast_" + UUID.randomUUID();
    int slowRuntime = 5000;
    int delayFastService = 500;

    try {
      // Dead lock happens, so cannot acquire lock.
      service.slowFirst_fastLater(slowName, fastName, slowRuntime, delayFastService);

      Assertions.fail("The above method should throw an exception because of dead lock.");
    } catch (CompletionException ex) {
      Throwable cause = ex.getCause();
      Assertions.assertTrue(cause instanceof CannotAcquireLockException);
    }
  }
  //
  //  @Test
  //  public void test_startFastBeforeSlow() throws ExecutionException, InterruptedException {
  //    ZonedDateTime startTime = ZonedDateTime.now();
  //
  //    String slowName = "Slow_" + UUID.randomUUID();
  //    String fastName = "Fast_" + UUID.randomUUID();
  //    int slowRuntime = 5000;
  //    int delayFastService = 500;
  //
  //    Pr05_01_MainService.Result result = service.fastFirst_slowLater(fastName, slowName, slowRuntime, delayFastService);
  //
  //    SimpleEntity slowEntity = simpleRepository.findByName(slowName).get();
  //    SimpleEntity fastEntity = simpleRepository.findByName(fastName).get();
  //
  //    log.info("startTime: {}", startTime);
  //    log.info("slowEntity: {}", slowEntity);
  //    log.info("fastEntity: {}", fastEntity);
  //    Duration fastAndSlowDiff = Duration.between(fastEntity.getCreatedDateTime(), slowEntity.getCreatedDateTime());
  //    log.info("fastAndSlowDiff: {} millis", fastAndSlowDiff.toMillis());
  //
  //    Duration startTime_to_finishFastEntity_Duration = Duration.between(startTime, fastEntity.getCreatedDateTime());
  //    log.info("startTime_to_finishFastEntity: {} millis == {} seconds",
  //        startTime_to_finishFastEntity_Duration.toMillis(),
  //        startTime_to_finishFastEntity_Duration.toSeconds());
  //
  //    Assertions.assertTrue(fastAndSlowDiff.toMillis() >= (long) (delayFastService - 300));
  //  }

  private String format(ZonedDateTime zonedDateTime) {
    return zonedDateTime.toString();
    //    return TimeZoneUtils.formatAtLocalZoneId(zonedDateTime.toZonedDateTime(), "HH:mm:ss,SSS");
  }
}
