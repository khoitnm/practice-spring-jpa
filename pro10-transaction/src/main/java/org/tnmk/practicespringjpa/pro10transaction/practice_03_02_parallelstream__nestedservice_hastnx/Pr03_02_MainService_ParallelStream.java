package org.tnmk.practicespringjpa.pro10transaction.practice_03_02_parallelstream__nestedservice_hastnx;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.tnmk.practicespringjpa.pro10transaction.common.SimpleEntity;
import org.tnmk.practicespringjpa.pro10transaction.common.SimpleRepository;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class Pr03_02_MainService_ParallelStream {

  private final Pr03_02_NestedService_TnxREQUIRED nestedService;
  private final SimpleRepository simpleRepository;

  @Transactional
  public void saveEntities_NoCatchEx(
      String alwaysSuccessName_InMainService_BeforeParallel,
      String alwaysSuccessName_InMainService_AfterParallel,
      List<String> parallelEntityNames
  ) throws IllegalArgumentException {
    // Note: when working with transaction, catching exception in main service is different from catching in nested service.

    simpleRepository.save(new SimpleEntity(alwaysSuccessName_InMainService_BeforeParallel));

    parallelEntityNames.stream()
        .parallel() // this is important for this practice: save entities in parallel.
        .forEach(entityName -> {
          nestedService.save_withTnxRequired(entityName);
        });

    simpleRepository.save(new SimpleEntity(alwaysSuccessName_InMainService_AfterParallel));
  }

  @Transactional
  public void saveEntities_CatchEx(
      String alwaysSuccessName_InMainService_BeforeParallel,
      String alwaysSuccessName_InMainService_AfterParallel,
      List<String> parallelEntityNames
  ) throws IllegalArgumentException {
    // Note: when working with transaction, catching exception in main service is different from catching in nested service.
    log.info("transaction {}", TransactionSynchronizationManager.getCurrentTransactionName());

    simpleRepository.save(new SimpleEntity(alwaysSuccessName_InMainService_BeforeParallel));

    parallelEntityNames.stream()
        .parallel() // this is important for this practice: save entities in parallel.
        .forEach(entityName -> {
          try {
            nestedService.save_withTnxRequired(entityName);
          } catch (Exception ex) {
            log.warn(ex.getMessage());
          }
        });

    simpleRepository.save(new SimpleEntity(alwaysSuccessName_InMainService_AfterParallel));
  }
}
