package org.tnmk.practicespringjpa.pro10transaction.practice_05_01_transaction_deadlock_when_savingSameEntity_with_isolationSERIALIZABLE;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.tnmk.practicespringjpa.pro10transaction.common.SimpleEntity;
import org.tnmk.practicespringjpa.pro10transaction.common.SimpleRepository;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class Pr05_01_FastService_Async {

  private final SimpleRepository simpleRepository;

  @Async
  @Transactional(isolation = Isolation.SERIALIZABLE)
  public CompletableFuture<ZonedDateTime> async_createEntity_fast(SimpleEntity simpleEntity) {
    log.info("edit fast entity: start");

    ZonedDateTime start = ZonedDateTime.now();

    simpleEntity = simpleRepository.save(simpleEntity);
    log.info("edit fast entity: saved");

    ZonedDateTime end = ZonedDateTime.now();
    Duration duration = Duration.between(start, end);
    log.info("edit fast entity: finished in {}", (double) duration.toMillis() / 1000d);
    return CompletableFuture.completedFuture(end);
  }
}
