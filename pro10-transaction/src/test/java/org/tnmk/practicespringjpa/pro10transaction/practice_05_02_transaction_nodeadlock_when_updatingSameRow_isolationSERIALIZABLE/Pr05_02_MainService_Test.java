package org.tnmk.practicespringjpa.pro10transaction.practice_05_02_transaction_nodeadlock_when_updatingSameRow_isolationSERIALIZABLE;

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
public class Pr05_02_MainService_Test extends BaseSpringTest_WithActualDb {
  @Autowired
  private Pr05_02_MainService service;

  @Autowired
  private SimpleRepository simpleRepository;

  @Test
  public void test_startSlowBeforeFast() throws ExecutionException, InterruptedException {
    ZonedDateTime startTime = ZonedDateTime.now();

    log.info("Timezone: " + TimeZone.getDefault());

    String slowName = "Slow_" + UUID.randomUUID();
    String fastName = "Fast_" + UUID.randomUUID();
    int slowRuntime = 5000;
    int delayFastService = 500;

    Pr05_02_MainService.Result result = service.slowFirst_fastLater(slowName, fastName, slowRuntime, delayFastService);

    SimpleEntity simpleEntity = simpleRepository.findById(result.getSimpleEntityId()).get();

    log.info("startTime:\t{}", format(startTime));
    log.info("simpleEntity: {}", simpleEntity);
    log.info("slow transaction finish: {}", result.getSlowFinishDateTime());
    log.info("fast transaction finish: {}", result.getFastFinishDateTime());

    // The 2nd transaction cannot be committed before the 1st transaction is committed.
    // But the createdDateTime in 2nd transaction actually can be before 1st transaction's committed date time.
    //
    // Anyway, because of transaction block, the fast transaction (2nd transaction) is finished (committed)
    // after the slow transaction (1st transaction).
    Assertions.assertTrue(result.getSlowFinishDateTime().isBefore(result.getFastFinishDateTime()));

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
//    Pr05_02_MainService.Result result = service.fastFirst_slowLater(fastName, slowName, slowRuntime, delayFastService);
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
