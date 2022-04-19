package org.tnmk.practicespringjpa.pro10transaction.practice_05_05_transaction_nodeadlock_when_saveEntityRollbackProblem;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.tnmk.practicespringjpa.pro10transaction.common.SimpleEntity;
import org.tnmk.practicespringjpa.pro10transaction.common.SimpleRepository;
import org.tnmk.practicespringjpa.pro10transaction.testinfra.BaseSpringTest_WithActualDb;

import java.util.Optional;
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
    public void test_editFirstAndThenInsertDuplicateValueLater__butThen_InsertSuccessWhileEditingFailed() throws ExecutionException, InterruptedException {
        log.info("Timezone: " + TimeZone.getDefault());

        String originalName = "A_" + UUID.randomUUID();
        String updateName = "B_" + UUID.randomUUID();
        int serviceRuntime = 2000;
        int delayBetweenServices = 1000;

        try {
            Pr05_05_MainService.Result result = service.editFirst_insertDuplicateLater(originalName, updateName, serviceRuntime, delayBetweenServices);
        } catch (CompletionException ex) {
            Assertions.assertTrue(ex.getCause() instanceof InvalidDataAccessResourceUsageException);

            // As we see in bellow assertions:
            // The above process caused an entity to be added into the external system.
            // But that entity is not the same as the corresponding entity in DB (different Ids, same name)
            Optional<SimpleEntity> entityInExternalSystem = externalSystemSimulator.findEntityByName(updateName);
            Assertions.assertTrue(entityInExternalSystem.isPresent());

            Optional<SimpleEntity> entityInDB = simpleRepository.findByName(updateName);
            Assertions.assertNotEquals(entityInExternalSystem.get().getId(), entityInDB.get().getId());
        }
    }
}
