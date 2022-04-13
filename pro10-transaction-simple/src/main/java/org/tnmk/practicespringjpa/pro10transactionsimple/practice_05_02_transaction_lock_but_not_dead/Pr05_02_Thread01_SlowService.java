package org.tnmk.practicespringjpa.pro10transactionsimple.practice_05_02_transaction_lock_but_not_dead;

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
public class Pr05_02_Thread01_SlowService {

  private final SimpleRepository simpleRepository;

  @Async
  @Transactional(isolation = Isolation.SERIALIZABLE)
  public CompletableFuture<ZonedDateTime> async_editEntity_Slow(SimpleEntity simpleEntity, int delayMillis) {
    ZonedDateTime start = ZonedDateTime.now();

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