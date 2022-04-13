package org.tnmk.practicespringjpa.pro10transactionsimple.practice_05_00_transaction_block;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SimpleEntity;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SimpleRepository;
import org.tnmk.practicespringjpa.pro10transactionsimple.testinfra.BaseSpringTest_WithActualDb;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Slf4j
public class Pr05_00_MainService_Test extends BaseSpringTest_WithActualDb {
  @Autowired
  private Pr05_00_MainService service;

  @Autowired
  private SimpleRepository simpleRepository;

  @Test
  public void test_startSlowBeforeFast() throws ExecutionException, InterruptedException {
    ZonedDateTime startTime = ZonedDateTime.now();

    log.info("Timezone: "+TimeZone.getDefault());

    String slowName = "Slow_" + UUID.randomUUID();
    String fastName = "Fast_" + UUID.randomUUID();
    int slowRuntime = 5000;
    int delayFastService = 500;

    Pr05_00_MainService.Result result = service.slowFirst_fastLater(slowName, fastName, slowRuntime, delayFastService);

    SimpleEntity slowEntity = simpleRepository.findByName(slowName).get();
    SimpleEntity fastEntity = simpleRepository.findByName(fastName).get();

    ZonedDateTime slowCreatedDateTime = slowEntity.getCreatedDateTime();
    ZonedDateTime fastCreatedDateTime = fastEntity.getCreatedDateTime();
    log.info("startTime:\t{}", format(startTime));
    log.info("slowEntity:\tcreatedDateTime: {}, finishedDateTime: {}", format(slowCreatedDateTime), format(result.getSlowFinishDateTime()));
    log.info("fastEntity:\tcreatedDateTime: {}, finishedDateTime: {}", format(fastCreatedDateTime), format(result.getFastFinishDateTime()));
    log.info("slow finish date before fast created date:\t{}", result.getSlowFinishDateTime().isBefore(fastCreatedDateTime));

    // The 2nd transaction cannot be committed before the 1st transaction is committed.
    // But the createdDateTime in 2nd transaction actually can be before 1st transaction's committed date time.
    //
    // Anyway, because of transaction block, the fast transaction (2nd transaction) is finished (committed)
    // after the slow transaction (1st transaction).
    Assertions.assertTrue(result.getSlowFinishDateTime().isBefore(fastCreatedDateTime));
    Assertions.assertTrue(result.getSlowFinishDateTime().isBefore(result.getFastFinishDateTime()));
  }

  @Test
  public void test_startFastBeforeSlow() throws ExecutionException, InterruptedException {
    ZonedDateTime startTime = ZonedDateTime.now();

    String slowName = "Slow_" + UUID.randomUUID();
    String fastName = "Fast_" + UUID.randomUUID();
    int slowRuntime = 5000;
    int delayFastService = 500;

    Pr05_00_MainService.Result result = service.fastFirst_slowLater(fastName, slowName, slowRuntime, delayFastService);

    SimpleEntity slowEntity = simpleRepository.findByName(slowName).get();
    SimpleEntity fastEntity = simpleRepository.findByName(fastName).get();

    log.info("startTime: {}", startTime);
    log.info("slowEntity: {}", slowEntity);
    log.info("fastEntity: {}", fastEntity);
    Duration fastAndSlowDiff = Duration.between(fastEntity.getCreatedDateTime(), slowEntity.getCreatedDateTime());
    log.info("fastAndSlowDiff: {} millis", fastAndSlowDiff.toMillis());

    Duration startTime_to_finishFastEntity_Duration = Duration.between(startTime, fastEntity.getCreatedDateTime());
    log.info("startTime_to_finishFastEntity: {} millis == {} seconds",
        startTime_to_finishFastEntity_Duration.toMillis(),
        startTime_to_finishFastEntity_Duration.toSeconds());

    Assertions.assertTrue(fastAndSlowDiff.toMillis() >= (long) (delayFastService - 300));
  }

  private String format(ZonedDateTime zonedDateTime) {
    return zonedDateTime.toString();
//    return TimeZoneUtils.formatAtLocalZoneId(zonedDateTime.toZonedDateTime(), "HH:mm:ss,SSS");
  }
}
