package org.tnmk.practicespringjpa.pro10transactionsimple.practice_00_01_caught_ex_wont_roll_back;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SimpleEntity;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SimpleRepository;

import javax.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class Pr00_01_MainService {
  private final SimpleRepository simpleRepository;


  @Transactional
  public void saveEntities(
      String alwaysSuccessName_InMainService,
      String entityName_InMainService
  ) throws IllegalArgumentException {
    // Note: when working with transaction, catching exception in main service is different from catching in nested service.

    SimpleEntity alwaysSuccessEntity = simpleRepository.save(new SimpleEntity(alwaysSuccessName_InMainService));

    try {
      SimpleEntity entity_InMainService = saveEntityWithNotNullName(entityName_InMainService);
    } catch (Exception ex) {
      // The exception is already caught, so the transaction won't be rolled back.
      log.warn(ex.getMessage(), ex);
    }
  }

  private SimpleEntity saveEntityWithNotNullName(String entityName) {
    if (entityName == null) {
      throw new IllegalArgumentException("name cannot be null");
    }
    return simpleRepository.save(new SimpleEntity(entityName));
  }
}
