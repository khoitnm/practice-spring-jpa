package org.tnmk.practicespringjpa.pro10transactionsimple.practice_05_02_transaction_nodeadlock_whenEditingSameEntity_isolationREADREPEEATED;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SimpleEntity;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SimpleRepository;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.utils.ThreadUtils;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class Pr05_02_SlowService_Async {

  private final SimpleRepository simpleRepository;

  @Async
  @Transactional(isolation = Isolation.REPEATABLE_READ)
  public CompletableFuture<ZonedDateTime> async_editEntity_Slow(String newName, long entityId, int delayMillis) {
    log.info("edit slow entity: start");

    ZonedDateTime start = ZonedDateTime.now();

    simpleRepository.updateNameById(newName, entityId);
    log.info("edit slow entity: saved");

    ThreadUtils.sleep(delayMillis);

    ZonedDateTime end = ZonedDateTime.now();
    Duration duration = Duration.between(start, end);
    log.info("edit slow entity: finished in {}s", (double) duration.toMillis() / 1000d);
    return CompletableFuture.completedFuture(end);
  }
}
;