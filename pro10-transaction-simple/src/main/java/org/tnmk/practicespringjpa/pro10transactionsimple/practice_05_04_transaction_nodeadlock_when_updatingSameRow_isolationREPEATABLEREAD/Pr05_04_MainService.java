package org.tnmk.practicespringjpa.pro10transactionsimple.practice_05_04_transaction_nodeadlock_when_updatingSameRow_isolationREPEATABLEREAD;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SimpleEntity;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SimpleRepository;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.utils.ThreadUtils;

import java.time.ZonedDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class Pr05_04_MainService {

  private final SimpleRepository simpleRepository;
  private final Pr05_04_FastService_Async fastService;
  private final Pr05_04_SlowService_Async slowService;

  public Result slowFirst_fastLater(String slowName, String fastName, int slowRuntimeMillis, int deplay2ndServiceInMillis)
      throws ExecutionException, InterruptedException {
    SimpleEntity simpleEntity = new SimpleEntity("Init");
    simpleEntity = simpleRepository.save(simpleEntity);

    CompletableFuture<ZonedDateTime> slowFuture = slowService.async_editEntity_Slow(slowName, simpleEntity.getId(), slowRuntimeMillis);

    //This small delay will make sure that the DB request from the previous thread will go to DB Server first before starting the second thread.
    ThreadUtils.sleep(deplay2ndServiceInMillis);

    CompletableFuture<ZonedDateTime> fastFuture = fastService.async_createEntity_fast(fastName, simpleEntity.getId());

    CompletableFuture.allOf(slowFuture, fastFuture).join();

    return new Result(simpleEntity.getId(), slowFuture.get(), fastFuture.get());
  }

  //  public Result fastFirst_slowLater(String fastName, String slowName, int slowRuntimeMillis, int deplay2ndServiceInMillis)
  //      throws ExecutionException, InterruptedException {
  //    CompletableFuture<ZonedDateTime> fastFuture = fastService.async_createEntity_fast(fastName);
  //
  //    //This make sure the DB request from the previous thread will go to DB Server first.
  //    ThreadUtils.sleep(deplay2ndServiceInMillis);
  //
  //    CompletableFuture<ZonedDateTime> slowFuture = slowService.async_editEntity_Slow(slowName, slowRuntimeMillis);
  //
  //    CompletableFuture.allOf(slowFuture, fastFuture).join();
  //
  //    return new Result(slowFuture.get(), fastFuture.get());
  //  }

  @RequiredArgsConstructor
  @Getter
  public static class Result {
    private final long simpleEntityId;
    private final ZonedDateTime slowFinishDateTime;
    private final ZonedDateTime fastFinishDateTime;
  }
}
