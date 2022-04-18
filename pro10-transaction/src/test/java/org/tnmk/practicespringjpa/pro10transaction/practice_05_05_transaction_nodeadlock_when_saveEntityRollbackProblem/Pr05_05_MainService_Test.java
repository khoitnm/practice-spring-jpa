package org.tnmk.practicespringjpa.pro10transaction.practice_05_05_transaction_nodeadlock_when_saveEntityRollbackProblem;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.tnmk.practicespringjpa.pro10transaction.common.SimpleRepository;
import org.tnmk.practicespringjpa.pro10transaction.testinfra.BaseSpringTest_WithActualDb;

import java.time.ZonedDateTime;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;

@Slf4j
public class Pr05_05_MainService_Test extends BaseSpringTest_WithActualDb {
    @Autowired
    private Pr05_05_MainService service;

    @Autowired
    private SimpleRepository simpleRepository;
    @Autowired
    private Pr05_05_ExternalSystemSimulator externalSystemSimulator;

    @Test
    public void test_startSlowBeforeFast() throws ExecutionException, InterruptedException {
        ZonedDateTime startTime = ZonedDateTime.now();

        log.info("Timezone: " + TimeZone.getDefault());

        String insertedName = "ToBeUpdatedName" + UUID.randomUUID();
        int serviceRuntime = 2000;
        int delayBetweenServices = 1000;


        try {
            Pr05_05_MainService.Result result = service.editFirst_insertDuplicateLater(insertedName, serviceRuntime, delayBetweenServices);
        } catch (CompletionException ex) {
            Assertions.assertTrue(ex.getCause() instanceof InvalidDataAccessResourceUsageException);
            Assertions.assertTrue(externalSystemSimulator.hasItem(insertedName));
        }
    }
}
