package org.tnmk.practicespringjpa.pro10transactionsimple.practice_05_00_transaction_block;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SimpleEntity;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SimpleRepository;

import javax.transaction.Transactional;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class Pr05_00_Thread02_FastService {

  private final SimpleRepository simpleRepository;

  @Async
  @Transactional
  public CompletableFuture<SimpleEntity> async_createEntity_fast(String name) {
    SimpleEntity simpleEntity = new SimpleEntity(name);
    simpleEntity = simpleRepository.save(simpleEntity);
    return CompletableFuture.completedFuture(simpleEntity);
  }
}
