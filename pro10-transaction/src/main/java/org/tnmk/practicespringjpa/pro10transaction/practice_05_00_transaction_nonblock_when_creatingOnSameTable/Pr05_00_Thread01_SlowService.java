package org.tnmk.practicespringjpa.pro10transaction.practice_05_00_transaction_nonblock_when_creatingOnSameTable;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.tnmk.practicespringjpa.pro10transaction.common.SimpleEntity;
import org.tnmk.practicespringjpa.pro10transaction.common.SimpleRepository;
import org.tnmk.practicespringjpa.pro10transaction.common.utils.ThreadUtils;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class Pr05_00_Thread01_SlowService {

  private final SimpleRepository simpleRepository;

  @Async
  @Transactional(isolation = Isolation.SERIALIZABLE)
  public CompletableFuture<ZonedDateTime> async_createEntity_Slow(String name, int delayMillis) {
    ZonedDateTime start = ZonedDateTime.now();

    SimpleEntity simpleEntity = new SimpleEntity(name);
    simpleEntity = simpleRepository.save(simpleEntity);
    log.info("create slow entity: saved");

    ThreadUtils.sleep(delayMillis);

    ZonedDateTime end = ZonedDateTime.now();
    Duration duration = Duration.between(start, end);
    log.info("create slow entity: finished in {}", (double) duration.toMillis() / 1000d);
    return CompletableFuture.completedFuture(end);
  }
}
;