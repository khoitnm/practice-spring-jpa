package org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr01_fullUpdate_vs_partialUpdate.pr01_01_partialUpdate_ExpectedData;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class Pr01_01_ChangeName_byPartialUpdate_Service {
    private final Pr01_01_SimpleRepository simpleRepository;

    @Async
    public CompletableFuture<Void> async_NoTnx_ChangeName(long entityId, String updateName) {
        simpleRepository.updateName(entityId, updateName);
        return CompletableFuture.completedFuture(null);
    }
}
