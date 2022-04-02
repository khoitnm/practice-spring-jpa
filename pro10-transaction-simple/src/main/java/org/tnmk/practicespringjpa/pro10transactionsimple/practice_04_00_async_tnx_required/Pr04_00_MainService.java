package org.tnmk.practicespringjpa.pro10transactionsimple.practice_04_00_async_tnx_required;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SimpleEntity;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SimpleRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class Pr04_00_MainService {

  private final Pr04_00_NestedService_Async_TnxREQUIRED nestedService;
  private final SimpleRepository simpleRepository;

  @Transactional
  public void saveEntities_NoCatchEx(
      String alwaysSuccessName_InMainService_BeforeParallel,
      String alwaysSuccessName_InMainService_AfterParallel,
      List<String> parallelEntityNames
  ) throws IllegalArgumentException {
    // Note: when working with transaction, catching exception in main service is different from catching in nested service.
    log.info("saveEntities_NoCatchEx: transaction {}",TransactionSynchronizationManager.getCurrentTransactionName());

    saveAlwaysSuccess(alwaysSuccessName_InMainService_BeforeParallel);

    CompletableFuture[] futures = parallelEntityNames.stream()
        .map(entityName ->
            nestedService.async_save_withTnxRequired(entityName))
        .toArray(CompletableFuture[]::new);

    CompletableFuture.allOf(futures).join();

    saveAlwaysSuccess(alwaysSuccessName_InMainService_AfterParallel);
  }

  @Transactional
  public void saveEntities_CatchEx(
      String alwaysSuccessName_InMainService_BeforeParallel,
      String alwaysSuccessName_InMainService_AfterParallel,
      List<String> parallelEntityNames
  ) throws IllegalArgumentException {
    // Note: when working with transaction, catching exception in main service is different from catching in nested service.

    saveAlwaysSuccess(alwaysSuccessName_InMainService_BeforeParallel);

    CompletableFuture[] futures = parallelEntityNames.stream()
        .map(entityName ->
            nestedService.async_save_withTnxRequired(entityName))
        .toArray(CompletableFuture[]::new);

    try {
      CompletableFuture.allOf(futures).join();
    } catch (Exception ex) {
      log.warn(ex.getMessage());
    }

    saveAlwaysSuccess(alwaysSuccessName_InMainService_AfterParallel);
  }

  private SimpleEntity saveAlwaysSuccess(String entityName) {
    log.info("saveAlwaysSuccess: transaction {}", TransactionSynchronizationManager.getCurrentTransactionName());
    return simpleRepository.save(new SimpleEntity(entityName));
  }
}
