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
import java.util.UUID;

@Slf4j
public class Pr05_00_MultiThread_Service_Test extends BaseSpringTest_WithActualDb {
  @Autowired
  private Pr05_00_MultiThread_Service service;

  @Autowired
  private SimpleRepository simpleRepository;

  @Test
  public void test_startSlowBeforeFast() {
    ZonedDateTime startTime = ZonedDateTime.now();

    String slowName = "Slow_" + UUID.randomUUID();
    String fastName = "Fast_" + UUID.randomUUID();
    int slowRuntime = 5000;
    int delayFastService = 500;

    service.slowFirst_fastLater(slowName, fastName, slowRuntime, delayFastService);

    SimpleEntity slowEntity = simpleRepository.findByName(slowName).get();
    SimpleEntity fastEntity = simpleRepository.findByName(fastName).get();

    log.info("startTime: {}", startTime);
    log.info("slowEntity: {}", slowEntity);
    log.info("fastEntity: {}", fastEntity);
    Assertions.assertTrue(slowEntity.getCreatedDateTime().isBefore(fastEntity.getCreatedDateTime()));
  }

  @Test
  public void test_startFastBeforeSlow() {
    ZonedDateTime startTime = ZonedDateTime.now();

    String slowName = "Slow_" + UUID.randomUUID();
    String fastName = "Fast_" + UUID.randomUUID();
    int slowRuntime = 5000;
    int delayFastService = 500;

    service.fastFirst_slowLater(fastName, slowName, slowRuntime, delayFastService);

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
}
