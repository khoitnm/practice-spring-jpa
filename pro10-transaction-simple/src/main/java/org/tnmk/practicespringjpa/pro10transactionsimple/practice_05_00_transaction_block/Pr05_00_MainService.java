package org.tnmk.practicespringjpa.pro10transactionsimple.practice_05_00_transaction_block;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SimpleEntity;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.utils.ThreadUtils;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class Pr05_00_MainService {

  private final Pr05_00_Thread02_FastService fastService;
  private final Pr05_00_Thread01_SlowService slowService;

  public Result slowFirst_fastLater(String slowName, String fastName, int slowRuntimeMillis, int deplay2ndServiceInMillis)
      throws ExecutionException, InterruptedException {
    CompletableFuture<OffsetDateTime> slowFuture = slowService.async_createEntity_Slow(slowName, slowRuntimeMillis);

    //This make sure the DB request from the previous thread will go to DB Server first.
    ThreadUtils.sleep(deplay2ndServiceInMillis);

    CompletableFuture<OffsetDateTime> fastFuture = fastService.async_createEntity_fast(fastName);

    CompletableFuture.allOf(slowFuture, fastFuture).join();

    return new Result(slowFuture.get(), fastFuture.get());
  }

  public Result fastFirst_slowLater(String fastName, String slowName, int slowRuntimeMillis, int deplay2ndServiceInMillis)
      throws ExecutionException, InterruptedException {
    CompletableFuture<OffsetDateTime> fastFuture = fastService.async_createEntity_fast(fastName);

    //This make sure the DB request from the previous thread will go to DB Server first.
    ThreadUtils.sleep(deplay2ndServiceInMillis);

    CompletableFuture<OffsetDateTime> slowFuture = slowService.async_createEntity_Slow(slowName, slowRuntimeMillis);

    CompletableFuture.allOf(slowFuture, fastFuture).join();

    return new Result(slowFuture.get(), fastFuture.get());
  }

  @RequiredArgsConstructor
  @Getter
  public static class Result {
    private final OffsetDateTime slowFinishDateTime;
    private final OffsetDateTime fastFinishDateTime;
  }
}
