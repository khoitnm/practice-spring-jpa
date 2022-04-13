package org.tnmk.practicespringjpa.pro10transactionsimple.practice_05_00_transaction_block;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SimpleEntity;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SimpleRepository;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.ZonedDateTime;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class Pr05_00_Thread02_FastService {

  private final SimpleRepository simpleRepository;

  @Async
  @Transactional
  public CompletableFuture<ZonedDateTime> async_createEntity_fast(String name) {
    ZonedDateTime start = ZonedDateTime.now();

    SimpleEntity simpleEntity = new SimpleEntity(name);
    simpleEntity = simpleRepository.save(simpleEntity);

    ZonedDateTime end = ZonedDateTime.now();
    Duration duration = Duration.between(start, end);
    log.info("create fast entity: finished in {}", (double) duration.toMillis() / 1000d);
    return CompletableFuture.completedFuture(end);
  }
}
