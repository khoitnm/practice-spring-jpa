package org.tnmk.practicespringjpa.pro10transactionsimple.practice_05_05_transaction_nodeadlock_when_saveEntityRollbackProblem;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SimpleRepository;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.utils.ThreadUtils;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class Pr05_05_ServiceNativeUpdate_Async {

    private final SimpleRepository simpleRepository;

    @Async
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public CompletableFuture<ZonedDateTime> async_nativeUpdateRow(String newName, long entityId, int delayMillis) {
        MDC.put("thread", "nativeUpdateRow");

        log.info("nativeUpdateRow: start");

        ZonedDateTime start = ZonedDateTime.now();

        simpleRepository.insertName(newName);
        log.info("nativeUpdateRow: saved");

//        ThreadUtils.sleep(delayMillis);
//        log.info("nativeUpdateRow: finish delay");

        ZonedDateTime end = ZonedDateTime.now();
        Duration duration = Duration.between(start, end);
        log.info("nativeUpdateRow: finished in {}s", (double) duration.toMillis() / 1000d);
        return CompletableFuture.completedFuture(end);
    }
}
;