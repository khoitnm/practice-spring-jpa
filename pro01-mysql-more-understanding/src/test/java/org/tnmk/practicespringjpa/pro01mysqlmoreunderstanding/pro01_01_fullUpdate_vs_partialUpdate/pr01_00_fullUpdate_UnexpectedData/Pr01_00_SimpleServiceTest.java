package org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pro01_01_fullUpdate_vs_partialUpdate.pr01_00_fullUpdate_UnexpectedData;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.common.SimpleEntity;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.common.SimpleRepository;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pro01_01_fullUpdate_vs_partialUpdate.Pr01_CreateEntityService;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.testinfra.BaseSpringTest_WithActualDb;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
public class Pr01_00_SimpleServiceTest extends BaseSpringTest_WithActualDb {
    @Autowired
    private Pr01_CreateEntityService createEntityService;

    @Autowired
    private Pr01_00_ChangeName_byFullUpdate_Service changeNameService;

    @Autowired
    private Pr01_00_Disable_byFullUpdate_Service disableService;

    @Autowired
    private SimpleRepository simpleRepository;

    @Test
    public void when_fullUpdate_eitherNameWontBeSavedAsExpected_Or_StatusWontBeSavedAsExpected() {
        // Given
        String initName = "A_" + UUID.randomUUID();
        String updateName = "B_" + UUID.randomUUID();

        // When
        SimpleEntity result = createEntityService.insert(initName);

        // Then
        CompletableFuture<Void> futureChangeName = changeNameService.async_NoTnx_ChangeName(result.getId(), updateName);
        CompletableFuture<Void> futureDisable = disableService.async_NoTnx_disableEntity(result.getId());

        CompletableFuture.allOf(futureChangeName, futureDisable).join();

        assert_Either_NameIsNotUpdatedAsExpected_Or_StatusIsNotUpdatedAsExpected(result.getId(), updateName, false);
    }

    private void assert_Either_NameIsNotUpdatedAsExpected_Or_StatusIsNotUpdatedAsExpected(long entityId, String updatedName, boolean updatedStatus) {
        log.info("Assertion entity with id {}... ", entityId);

        Optional<SimpleEntity> sampleEntityOptional = simpleRepository.findById(entityId);

        Assertions.assertEquals(true, sampleEntityOptional.isPresent());
        String actualNameInDB = sampleEntityOptional.get().getName();
        boolean actualStatusInDB = sampleEntityOptional.get().isEnabled();

        boolean sameName = updatedName.equals(actualNameInDB);
        boolean sameStatus = updatedStatus == actualStatusInDB;

        log.info("SampleEntity: " + sampleEntityOptional.get()
                + "\n\tactualName: '" + actualNameInDB + "' vs. expected updateName: '" + updatedName + "'"
                + "\n\tactualStatus: " + actualStatusInDB + " vs. expected status: " + updatedStatus);
        Assertions.assertTrue(!sameName || !sameStatus);
    }
}
