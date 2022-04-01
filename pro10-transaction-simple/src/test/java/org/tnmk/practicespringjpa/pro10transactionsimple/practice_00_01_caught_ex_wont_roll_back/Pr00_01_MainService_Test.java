package org.tnmk.practicespringjpa.pro10transactionsimple.practice_00_01_caught_ex_wont_roll_back;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SimpleEntity;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SimpleRepository;
import org.tnmk.practicespringjpa.pro10transactionsimple.testinfra.BaseSpringTest_WithActualDb;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

public class Pr00_01_MainService_Test extends BaseSpringTest_WithActualDb {
  @Autowired
  private Pr00_01_MainService mainService;

  @Autowired
  private SimpleRepository simpleRepository;

  @AfterEach
  @Transactional
  public void cleanUp() {
    simpleRepository.deleteAll();
  }

  @Test
  public void when_MainService_CatchAllExceptions_then_wontBeRolledBack() {
    // Given
    String alwaysSuccessName_InMainService = "alwaysSuccess_InMainService_" + UUID.randomUUID();

    // This null value will cause exception.
    // However, the logic inside mainService already caught all exception, so the outside transaction won't be rolled back.
    String entityName_InMainService = null;

    // When
    mainService.saveEntities(alwaysSuccessName_InMainService, entityName_InMainService);

    // Then
    assertExist(alwaysSuccessName_InMainService, true);
  }

  private void assertExist(String entityName, boolean expectExist) {
    Optional<SimpleEntity> sampleEntityOptional = simpleRepository.findByName(entityName);
    Assertions.assertEquals(expectExist, sampleEntityOptional.isPresent(), "Expect '" + entityName + "' existence to be " + expectExist);
  }
}
