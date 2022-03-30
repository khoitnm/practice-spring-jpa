package org.tnmk.practicespringjpa.pro01simplemysql.sample_business;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.practicespringjpa.pro01simplemysql.testinfra.BaseSpringTest_WithTestContainer;

import java.util.Optional;
import java.util.UUID;

public class SimpleServiceMainTest extends BaseSpringTest_WithTestContainer {
  @Autowired
  private SimpleService simpleServiceMain;

  @Autowired
  private SimpleRepository simpleRepository;

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
    Assert.assertEquals(expectExist, sampleEntityOptional.isPresent());
  }
}
