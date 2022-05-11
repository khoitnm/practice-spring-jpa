package org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pro01_01_fullUpdate_vs_partialUpdate.pr01_01_partialUpdate_ExpectedData;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class Pr01_01_Disable_byPartialUpdate_Service {

    private final Pr01_01_SimpleRepository simpleRepository;

    @Async
    public CompletableFuture<Void> async_NoTnx_DisableEntity(long entityId) {
        simpleRepository.disabledEntityById(entityId);
        return CompletableFuture.completedFuture(null);
    }
}
