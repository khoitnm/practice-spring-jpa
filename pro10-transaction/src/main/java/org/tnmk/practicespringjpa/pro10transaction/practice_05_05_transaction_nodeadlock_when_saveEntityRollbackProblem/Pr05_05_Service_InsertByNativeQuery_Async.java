package org.tnmk.practicespringjpa.pro10transaction.practice_05_05_transaction_nodeadlock_when_saveEntityRollbackProblem;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.tnmk.practicespringjpa.pro10transaction.common.SimpleRepository;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class Pr05_05_Service_InsertByNativeQuery_Async {

    private final SimpleRepository simpleRepository;

    @Async
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public CompletableFuture<ZonedDateTime> async_insertRow(String newName) {
        log.info("async_insertRow: start...");
        simpleRepository.insertName(newName);
        log.info("async_insertRow: finished!");
        return CompletableFuture.completedFuture(ZonedDateTime.now());
    }
}
;