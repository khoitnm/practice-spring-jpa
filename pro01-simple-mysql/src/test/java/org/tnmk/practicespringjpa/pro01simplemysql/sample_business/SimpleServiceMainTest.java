package org.tnmk.practicespringjpa.pro01simplemysql.sample_business;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.practicespringjpa.pro01simplemysql.testinfra.BaseSpringTest_WithActualDb;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
public class SimpleServiceMainTest extends BaseSpringTest_WithActualDb {
  @Autowired
  private SimpleService simpleServiceMain;

  @Autowired
  private SimpleRepository simpleRepository;

  @Test
  public void test_when_everything_is_saved_successfully() {
    // Given
    SimpleEntity simpleEntity = new SimpleEntity("Name" + UUID.randomUUID());
    simpleEntity.setCreatedDateTime(ZonedDateTime.now());

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
