package org.tnmk.practicespringjpa.pro10transactionsimple.practice_04_00_async_tnx_required;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SimpleEntity;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SimpleRepository;

import javax.transaction.Transactional;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class Pr04_00_NestedService_Async_TnxREQUIRED {
  private final SimpleRepository simpleRepository;

  @Async
  @Transactional(Transactional.TxType.REQUIRED) // this is the default type of transaction.
  public CompletableFuture<Void> async_save_withTnxRequired(String entityName) throws IllegalArgumentException {
    log.info("Saving '{}': starting ...", entityName);
    log.info("async_save_withTnxRequired {}: transaction {}", entityName, TransactionSynchronizationManager.getCurrentTransactionName());
    if (entityName == null) {
      throw new IllegalArgumentException("name cannot be null");
    }
    SimpleEntity result = simpleRepository.save(new SimpleEntity(entityName));
    log.info("Saving '{}': finished!", entityName);

    return CompletableFuture.completedFuture(null);
  }
}