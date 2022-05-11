package org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pro01_00_01_updateBySaveEntity_NoTnx_vs_Tnx;

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
public class Pr00_01_SimpleServiceTest extends BaseSpringTest_WithActualDb {
    @Autowired
    private Pr00_01_SimpleService_With_Tnx pr0001SimpleServiceWithTnx;

    @Autowired
    private Pr00_01_SimpleService_Without_Tnx pr0001SimpleServiceWithoutTnx;
    @Autowired
    private SimpleRepository simpleRepository;

    @Test
    public void test_WithoutTransaction() {
        // Given
        String initName = "Init_" + UUID.randomUUID();
        String updateName = "Update_" + UUID.randomUUID();

        // When
        SimpleEntity result = pr0001SimpleServiceWithoutTnx.insertAndUpdate(initName, updateName);

        // Then
        assertExistWithSameName(result.getId(), updateName);
    }

    @Test
    public void test_WithTransaction() {
        // Given
        String initName = "Init_" + UUID.randomUUID();
        String updateName = "Update_" + UUID.randomUUID();

        // When
        SimpleEntity result = pr0001SimpleServiceWithTnx.insertAndUpdate(initName, updateName);

        // Then
        assertExistWithSameName(result.getId(), updateName);
    }

    private void assertExistWithSameName(long entityId, String expectName) {
        log.info("Assert whether an entity with id {} has expected name '{}' or not... ", entityId, expectName);

        Optional<SimpleEntity> sampleEntityOptional = simpleRepository.findById(entityId);

        Assertions.assertEquals(true, sampleEntityOptional.isPresent());
        log.info("SampleEntity: " + sampleEntityOptional.get());

        Assertions.assertEquals(expectName, sampleEntityOptional.get().getName());
    }
}
