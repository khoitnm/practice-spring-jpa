package org.tnmk.practicespringjpa.pro10transaction.practice_05_03_transaction_nodeadlock_when_savingSameEntity_with_isolationREPEATREAD;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.tnmk.practicespringjpa.pro10transaction.common.SimpleEntity;
import org.tnmk.practicespringjpa.pro10transaction.common.SimpleRepository;
import org.tnmk.practicespringjpa.pro10transaction.common.utils.ThreadUtils;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class Pr05_03_SlowService_Async {

  private final SimpleRepository simpleRepository;

  @Async
  @Transactional(isolation = Isolation.REPEATABLE_READ)
  public CompletableFuture<ZonedDateTime> async_editEntity_Slow(SimpleEntity simpleEntity, int delayMillis) {
    log.info("edit slow entity: start " + TransactionSynchronizationManager.getCurrentTransactionName());

    ZonedDateTime start = ZonedDateTime.now();

    simpleEntity = simpleRepository.save(simpleEntity);
    log.info("edit slow entity: saved");

    ThreadUtils.sleep(delayMillis);

    ZonedDateTime end = ZonedDateTime.now();
    Duration duration = Duration.between(start, end);
    log.info("edit slow entity: finished in {} with transaction {}", (double) duration.toMillis() / 1000d,
        TransactionSynchronizationManager.getCurrentTransactionName());
    return CompletableFuture.completedFuture(end);
  }
}
;