package org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pro01_01_fullUpdate_vs_partialUpdate.pr01_01_partialUpdate_ExpectedData;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.common.SimpleEntity;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.common.SimpleRepository;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pro01_01_fullUpdate_vs_partialUpdate.Pr01_CreateEntityService;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pro01_01_fullUpdate_vs_partialUpdate.pr01_00_fullUpdate_UnexpectedData.Pr01_00_SimpleServiceTest;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.testinfra.BaseSpringTest_WithActualDb;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
public class Pr01_01_SimpleServiceTest extends BaseSpringTest_WithActualDb {
    @Autowired
    private Pr01_CreateEntityService createEntityService;

    @Autowired
    private Pr01_01_ChangeName_byPartialUpdate_Service changeNameService;

    @Autowired
    private Pr01_01_Disable_byPartialUpdate_Service disableService;

    @Autowired
    private SimpleRepository simpleRepository;

    /**
     * The logic of this test case is the similar as {@link Pr01_00_SimpleServiceTest#when_fullUpdate_eitherNameWontBeSavedAsExpected_Or_StatusWontBeSavedAsExpected()}
     * but as we see, the result is diffirent when using partial update: the data is now saved as expected.
     */
    @Test
    public void when_partialUpdate_bothNameAndStatusWillBeSavedAsExpected() {
        // Given
        String initName = "Init_" + UUID.randomUUID();
        String updateName = "Update_" + UUID.randomUUID();

        // When
        SimpleEntity result = createEntityService.insert(initName);

        // Then
        CompletableFuture<Void> futureChangeName = changeNameService.async_NoTnx_ChangeName(result.getId(), updateName);
        CompletableFuture<Void> futureDisable = disableService.async_NoTnx_DisableEntity(result.getId());

        CompletableFuture.allOf(futureChangeName, futureDisable).join();

        assert_bothNameAndStatusWillBeSavedAsExpected(result.getId(), updateName, false);
    }

    private void assert_bothNameAndStatusWillBeSavedAsExpected(long entityId, String updatedName, boolean updatedStatus) {
        Optional<SimpleEntity> sampleEntityOptional = simpleRepository.findById(entityId);

        Assertions.assertEquals(true, sampleEntityOptional.isPresent());
        String actualNameInDB = sampleEntityOptional.get().getName();
        boolean actualStatusInDB = sampleEntityOptional.get().isEnabled();

        log.info("SampleEntity: " + sampleEntityOptional.get()
                + "\n\tactualName: '" + actualNameInDB + "' vs. expected updateName: '" + updatedName + "'"
                + "\n\tactualStatus: " + actualStatusInDB + " vs. expected status: " + updatedStatus);
        Assertions.assertEquals(updatedName, actualNameInDB);
        Assertions.assertEquals(updatedStatus, actualStatusInDB);
    }
}
