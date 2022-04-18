package org.tnmk.practicespringjpa.pro10transaction.practice_05_05_transaction_nodeadlock_when_saveEntityRollbackProblem;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringjpa.pro10transaction.common.SimpleEntity;
import org.tnmk.practicespringjpa.pro10transaction.common.SimpleRepository;
import org.tnmk.practicespringjpa.pro10transaction.common.utils.ThreadUtils;

import java.time.ZonedDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class Pr05_05_MainService {

    private final SimpleRepository simpleRepository;
    private final Pr05_05_Service_JpaSaveEntity_Async japSaveEntityService;
    private final Pr05_05_ServiceNativeUpdate_Async nativeUpdateService;

    public Result editFirst_insertDuplicateLater(String updateName, int slowRuntimeMillis, int deplay2ndServiceInMillis)
            throws ExecutionException, InterruptedException {
        MDC.put("thread", "main");
        SimpleEntity simpleEntity = new SimpleEntity("Init");
        simpleEntity = simpleRepository.save(simpleEntity);

        simpleEntity.setName(updateName);
        CompletableFuture<ZonedDateTime> fastFuture = japSaveEntityService.async_jpaSaveEntity(simpleEntity, slowRuntimeMillis);


        //This small delay will make sure that the DB request from the previous thread will go to DB Server first before starting the second thread.
        ThreadUtils.sleep(deplay2ndServiceInMillis);

        // This will cause duplicate error.
        CompletableFuture<ZonedDateTime> slowFuture = nativeUpdateService.async_nativeUpdateRow(updateName, simpleEntity.getId(), slowRuntimeMillis);

        CompletableFuture.allOf(slowFuture, fastFuture).join();

        return new Result(simpleEntity.getId(), slowFuture.get(), fastFuture.get());
    }

    @RequiredArgsConstructor
    @Getter
    public static class Result {
        private final long simpleEntityId;
        private final ZonedDateTime slowFinishDateTime;
        private final ZonedDateTime fastFinishDateTime;
    }
}
