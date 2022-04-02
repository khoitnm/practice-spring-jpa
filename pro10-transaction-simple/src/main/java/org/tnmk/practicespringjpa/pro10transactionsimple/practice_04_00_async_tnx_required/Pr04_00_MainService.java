package org.tnmk.practicespringjpa.pro10transactionsimple.practice_04_00_async_tnx_required;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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
        .map(entityName -> {
          try {
            return nestedService.async_save_withTnxRequired(entityName);
          } catch (Exception ex) {
            log.warn(ex.getMessage());
            return CompletableFuture.completedFuture(null);
          }
        })
        .toArray(CompletableFuture[]::new);

    CompletableFuture.allOf(futures).join();

    saveAlwaysSuccess(alwaysSuccessName_InMainService_AfterParallel);
  }

  private SimpleEntity saveAlwaysSuccess(String entityName) {
    return simpleRepository.save(new SimpleEntity(entityName));
  }
}
