package org.tnmk.practicespringjpa.pro10transactionsimple.practice_05_00_transaction_block;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SimpleEntity;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SimpleRepository;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.utils.ThreadUtils;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class Pr05_00_Thread01_SlowService {

  private final SimpleRepository simpleRepository;

  @Async
  @Transactional
  public CompletableFuture<OffsetDateTime> async_createEntity_Slow(String name, int delayMillis) {
    OffsetDateTime start = OffsetDateTime.now();

    SimpleEntity simpleEntity = new SimpleEntity(name);
    simpleEntity = simpleRepository.save(simpleEntity);
    log.info("create slow entity: saved");

    ThreadUtils.sleep(delayMillis);

    OffsetDateTime end = OffsetDateTime.now();
    Duration duration = Duration.between(start, end);
    log.info("create slow entity: finished in {}", (double) duration.toMillis() / 1000d);
    return CompletableFuture.completedFuture(end);
  }
}
;