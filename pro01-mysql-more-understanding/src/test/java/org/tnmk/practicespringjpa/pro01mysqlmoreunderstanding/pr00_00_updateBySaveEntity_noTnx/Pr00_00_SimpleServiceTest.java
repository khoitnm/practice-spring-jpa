package org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr00_00_updateBySaveEntity_noTnx;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.common.SimpleEntity;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.testinfra.BaseSpringTest_WithActualDb;

import java.util.Optional;
import java.util.UUID;

@Slf4j
public class Pr00_00_SimpleServiceTest extends BaseSpringTest_WithActualDb {
    @Autowired
    private Pr00_00_SimpleService simpleServiceMain;

    @Autowired
    private Pr00_00_SimpleRepository simpleRepository;

    @Test
    public void test_when_everything_is_saved_successfully() {
        // Given
        String name = "Name" + UUID.randomUUID();
        // When
        SimpleEntity result = simpleServiceMain.insertAndUpdate(name);

        // Then
        assertExist(result.getId(), true);
    }

    private void assertExist(long entityId, boolean expectExist) {
        Optional<SimpleEntity> sampleEntityOptional = simpleRepository.findById(entityId);

        Assertions.assertEquals(expectExist, sampleEntityOptional.isPresent());
        log.info("SampleEntity: " + sampleEntityOptional.get());

    }
}
