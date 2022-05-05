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
    private final Pr05_05_Service_UpdateByJpaSaveEntity_Async japSaveEntityService;
    private final Pr05_05_Service_InsertByNativeQuery_Async nativeUpdateService;

    public Result editFirst_insertDuplicateLater(String originalName, String updateName, int slowRuntimeMillis, int deplay2ndServiceInMillis)
            throws ExecutionException, InterruptedException {
        SimpleEntity simpleEntity = new SimpleEntity(originalName);
        simpleEntity = simpleRepository.save(simpleEntity);

        CompletableFuture<ZonedDateTime> fastFuture = japSaveEntityService.async_updateEntity(simpleEntity, updateName, slowRuntimeMillis);

        // This small delay will make sure that the previous thread send request to DB Server first before starting the second thread.
        ThreadUtils.sleep(deplay2ndServiceInMillis);

        // Even though this thread is started after the previous thread, it still inserts data successfully.
        // And because of that, it will make the previous thread get duplicate error when updating entity.
        CompletableFuture<ZonedDateTime> slowFuture = nativeUpdateService.async_insertRow(updateName);

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
