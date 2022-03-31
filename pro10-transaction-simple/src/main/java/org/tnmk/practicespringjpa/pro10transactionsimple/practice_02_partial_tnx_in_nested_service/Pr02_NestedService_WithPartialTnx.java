package org.tnmk.practicespringjpa.pro10transactionsimple.practice_02_partial_tnx_in_nested_service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SimpleEntity;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SimpleRepository;

import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class Pr02_NestedService_WithPartialTnx {
  private final TransactionTemplate transactionTemplate;
  private final SimpleRepository simpleRepository;

  /**
   * A "Partial Transaction" is a transaction that only cover a part of the business logic of this method,
   * it won't cover transaction for the whole method.
   */
  public NestedResult saveWithPartialTnx(
      String alwaysSuccessName_InNestedService_PartialTnx,
      String entity_InNestedService_PartialTnx,
      String entity_InNestedService_AfterPartialTnx) throws IllegalArgumentException {

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