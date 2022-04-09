package org.tnmk.practicespringjpa.pro10transactionsimple.practice_05_00_transaction_block;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SimpleEntity;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.utils.ThreadUtils;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class Pr05_00_MultiThread_Service {

  private final Pr05_00_Thread02_FastService fastService;
  private final Pr05_00_Thread01_SlowService slowService;

  public void slowFirst_fastLater(String slowName, String fastName, int slowRuntimeMillis, int deplay2ndServiceInMillis) {
    CompletableFuture<SimpleEntity> slowFuture = slowService.async_createEntity_Slow(slowName, slowRuntimeMillis);

    //This make sure the DB request from the previous thread will go to DB Server first.
    ThreadUtils.sleep(deplay2ndServiceInMillis);

    CompletableFuture<SimpleEntity> fastFuture = fastService.async_createEntity_fast(fastName);

    CompletableFuture.allOf(slowFuture, fastFuture).join();
  }

  public void fastFirst_slowLater(String fastName, String slowName, int slowRuntimeMillis, int deplay2ndServiceInMillis) {
    CompletableFuture<SimpleEntity> fastFuture = fastService.async_createEntity_fast(fastName);

    //This make sure the DB request from the previous thread will go to DB Server first.
    ThreadUtils.sleep(deplay2ndServiceInMillis);

    CompletableFuture<SimpleEntity> slowFuture = slowService.async_createEntity_Slow(slowName, slowRuntimeMillis);

    CompletableFuture.allOf(slowFuture, fastFuture).join();
  }
}
