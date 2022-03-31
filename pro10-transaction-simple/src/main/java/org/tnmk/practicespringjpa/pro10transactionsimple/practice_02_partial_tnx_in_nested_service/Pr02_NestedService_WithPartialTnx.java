package org.tnmk.practicespringjpa.pro10transactionsimple.practice_02_partial_tnx_in_nested_service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SimpleEntity;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SimpleRepository;

import javax.transaction.Transactional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class Pr02_NestedService_WithPartialTnx {
  private final TransactionTemplate transactionTemplate;
  private final SimpleRepository simpleRepository;

  @Transactional
  public SimpleEntity save(SimpleEntity toBeSavedInMainMethod, boolean isUpdateAfterSavingSuccessfully) throws IllegalArgumentException {
    AtomicReference<SimpleEntity> result = new AtomicReference<>();

    transactionTemplate.execute(status -> {
      simpleRepository.save(new SimpleEntity("AlwaysSuccessInNestedBusiness" + UUID.randomUUID()));

      result.set(saveEntityWithNotNullName(toBeSavedInMainMethod));
      return result;
    });

    SimpleEntity savedEntity = result.get();
    if (isUpdateAfterSavingSuccessfully) {
      savedEntity.setName(savedEntity.getName() + "Edited Successfully");
      return simpleRepository.save(savedEntity);
    } else {
      throw new IllegalArgumentException("As expected, we'll simulate that the update process will be failed after the previous saving.");
    }
  }

  private SimpleEntity saveEntityWithNotNullName(SimpleEntity entity) {
    if (entity.getName() == null) {
      throw new IllegalArgumentException("name cannot be null");
    }
    return simpleRepository.save(entity);
  }
}
