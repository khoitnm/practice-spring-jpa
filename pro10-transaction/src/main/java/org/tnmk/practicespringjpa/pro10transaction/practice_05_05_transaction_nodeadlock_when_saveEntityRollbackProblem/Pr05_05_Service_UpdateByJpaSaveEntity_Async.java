package org.tnmk.practicespringjpa.pro10transaction.practice_05_05_transaction_nodeadlock_when_saveEntityRollbackProblem;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.tnmk.practicespringjpa.pro10transaction.common.SimpleEntity;
import org.tnmk.practicespringjpa.pro10transaction.common.SimpleRepository;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class Pr05_05_Service_UpdateByJpaSaveEntity_Async {

    private final SimpleRepository simpleRepository;
    private final Pr05_05_ExternalSystemSimulator externalSystemSimulator;

    @Async
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public CompletableFuture<ZonedDateTime> async_updateEntity(SimpleEntity simpleEntity, String updateName, int delayMillis) {
        log.info("async_updateEntity: start...");

        log.info("save entity: start...");
        simpleEntity.setName(updateName);
        simpleRepository.save(simpleEntity);
        log.info("save entity: finished!");

        log.info("addEntity to externalSystem: starting...");
        externalSystemSimulator.addEntity(simpleEntity, delayMillis);
        log.info("addEntity to externalSystem: finished!!!");

        log.info("async_updateEntity: finished!!!");
        return CompletableFuture.completedFuture(ZonedDateTime.now());
    }
}
