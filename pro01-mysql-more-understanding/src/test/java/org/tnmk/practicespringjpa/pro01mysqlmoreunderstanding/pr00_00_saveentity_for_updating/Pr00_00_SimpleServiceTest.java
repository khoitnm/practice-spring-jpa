package org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr00_00_saveentity_for_updating;

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
    SimpleEntity simpleEntity = new SimpleEntity("Name" + UUID.randomUUID());

    // When
    SimpleEntity result = simpleServiceMain.saveEntities(simpleEntity);

    // Then
    assertExist(result.getId(), true);
  }

  private void assertExist(long entityId, boolean expectExist) {
    Optional<SimpleEntity> sampleEntityOptional = simpleRepository.findById(entityId);

    Assertions.assertEquals(expectExist, sampleEntityOptional.isPresent());
    log.info("SampleEntity: " + sampleEntityOptional.get());

  }
}
