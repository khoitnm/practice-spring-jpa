package org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr00_00_updateBySaveEntity_noTnx;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.common.SimpleEntity;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.common.SimpleRepository;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.testinfra.BaseSpringTest_WithActualDb;

import java.util.Optional;
import java.util.UUID;

@Slf4j
public class Pr00_00_SimpleServiceTest extends BaseSpringTest_WithActualDb {
    @Autowired
    private Pr00_00_SimpleService simpleServiceMain;

    @Autowired
    private SimpleRepository simpleRepository;

    @Test
    public void test_when_everything_is_saved_successfully() {
        // Given
        String initName = "Init_" + UUID.randomUUID();
        String updateName = "Update_" + UUID.randomUUID();

        // When
        SimpleEntity result = simpleServiceMain.insertAndUpdate(initName, updateName);

        // Then
        assertExistWithSameName(result.getId(), updateName);
    }

    private void assertExistWithSameName(long entityId, String expectName) {
        Optional<SimpleEntity> sampleEntityOptional = simpleRepository.findById(entityId);

        Assertions.assertEquals(true, sampleEntityOptional.isPresent());
        log.info("SampleEntity: " + sampleEntityOptional.get());

        Assertions.assertEquals(expectName, sampleEntityOptional.get().getName());
    }
}
