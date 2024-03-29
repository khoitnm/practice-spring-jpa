package org.tnmk.practicespringjpa.pro10transaction.practice_02_01_partial_tnx_in_nested_service__independant_tnx_by__tnx_requires_new;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;
import org.tnmk.practicespringjpa.pro10transaction.common.SimpleEntity;
import org.tnmk.practicespringjpa.pro10transaction.common.SimpleRepository;

import java.util.concurrent.atomic.AtomicReference;

@Service
public class Pr02_01_NestedService_WithPartialTnx {
  private final PlatformTransactionManager transactionManager;
  private final SimpleRepository simpleRepository;
  private final TransactionTemplate transactionTemplate;

  public Pr02_01_NestedService_WithPartialTnx(PlatformTransactionManager transactionManager,
      SimpleRepository simpleRepository) {
    this.transactionManager = transactionManager;
    this.simpleRepository = simpleRepository;

    transactionTemplate = new TransactionTemplate(transactionManager);
  }

  /**
   * A "Partial Transaction" is a transaction that only cover a part of the business logic of this method,
   * it won't cover transaction for the whole method.
   */
  public NestedResult saveWithPartialTnx(
      String alwaysSuccessName_InNestedService_PartialTnx,
      String entity_InNestedService_PartialTnx,
      String entity_InNestedService_AfterPartialTnx) throws IllegalArgumentException {

    transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

    AtomicReference<PartialTnxResult> result = new AtomicReference<>();
    // Start partial transaction
    transactionTemplate.execute(status -> {
      SimpleEntity alwaysSuccessInPartialTnx = simpleRepository.save(new SimpleEntity(alwaysSuccessName_InNestedService_PartialTnx));

      SimpleEntity saved = saveEntityWithNotNullName(entity_InNestedService_PartialTnx);

      result.set(new PartialTnxResult(alwaysSuccessInPartialTnx, saved));
      // returning in transaction execution is not important.
      return null;
    });
    // End partial transaction

    PartialTnxResult partialTnxResult = result.get();
    SimpleEntity savedEntity_InNestedService_AfterPartialTnx = saveEntityWithNotNullName(entity_InNestedService_AfterPartialTnx);
    return new NestedResult(partialTnxResult, savedEntity_InNestedService_AfterPartialTnx);
  }

  private SimpleEntity saveEntityWithNotNullName(String entityName) {
    if (entityName == null) {
      throw new IllegalArgumentException("name cannot be null");
    }
    return simpleRepository.save(new SimpleEntity(entityName));
  }

  @RequiredArgsConstructor
  @Getter
  public static class PartialTnxResult {
    private final SimpleEntity alwaysSuccess_InNestedService_PartialTnx;
    private final SimpleEntity entity_InNestedService_PartialTnx;
  }

  @RequiredArgsConstructor
  @Getter
  public static class NestedResult {
    private final PartialTnxResult partialTnxResult;
    private final SimpleEntity entity_InNestedService_AfterPartialTnx;
  }
}