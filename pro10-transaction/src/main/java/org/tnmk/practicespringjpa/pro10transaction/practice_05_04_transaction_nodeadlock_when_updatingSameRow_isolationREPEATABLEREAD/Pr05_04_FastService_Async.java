package org.tnmk.practicespringjpa.pro10transaction.practice_05_04_transaction_nodeadlock_when_updatingSameRow_isolationREPEATABLEREAD;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.tnmk.practicespringjpa.pro10transaction.common.SimpleRepository;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class Pr05_04_FastService_Async {

  private final SimpleRepository simpleRepository;

  @Async
  @Transactional(isolation = Isolation.REPEATABLE_READ)
  public CompletableFuture<ZonedDateTime> async_createEntity_fast(String newName, long entityId) {
    log.info("edit fast entity: start");

    ZonedDateTime start = ZonedDateTime.now();

    simpleRepository.updateNameById(newName, entityId);
    log.info("edit fast entity: saved");

    ZonedDateTime end = ZonedDateTime.now();
    Duration duration = Duration.between(start, end);
    log.info("edit fast entity: finished in {}s", (double) duration.toMillis() / 1000d);
    return CompletableFuture.completedFuture(end);
  }
}
