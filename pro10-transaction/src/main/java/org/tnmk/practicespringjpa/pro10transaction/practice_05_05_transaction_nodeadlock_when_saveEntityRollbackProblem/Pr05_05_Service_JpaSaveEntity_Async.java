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
public class Pr05_05_Service_JpaSaveEntity_Async {

    private final SimpleRepository simpleRepository;
    private final Pr05_05_ExternalSystemSimulator externalSystemSimulator;

    @Async
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public CompletableFuture<ZonedDateTime> async_updateEntity(SimpleEntity simpleEntity, String updateName, int delayMillis) {
        MDC.put("thread", "jpaSaveEntity");

        log.info("jpaSaveEntity: start");

        ZonedDateTime start = ZonedDateTime.now();

        simpleEntity.setName(updateName);
        simpleRepository.save(simpleEntity);
        log.info("jpaSaveEntity: saved");

        externalSystemSimulator.addItem(simpleEntity, delayMillis);

        ZonedDateTime end = ZonedDateTime.now();
        Duration duration = Duration.between(start, end);
        log.info("jpaSaveEntity: finished in {}s", (double) duration.toMillis() / 1000d);
        return CompletableFuture.completedFuture(end);
    }
}
