package org.tnmk.practicespringjpa.pro10transactionsimple.practice_03_01_parallelstream__nestedservice_notnx;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SimpleEntity;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SimpleRepository;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class Pr03_01_MainService_ParallelStream {

  private final Pr03_01_NestedService_NoTnx nestedService;
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
          nestedService.save(entityName);
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

    simpleRepository.save(new SimpleEntity(alwaysSuccessName_InMainService_BeforeParallel));

    parallelEntityNames.stream()
        .parallel() // this is important for this practice: save entities in parallel.
        .forEach(entityName -> {
          try {
            nestedService.save(entityName);
          } catch (Exception ex) {
            log.warn(ex.getMessage());
          }
        });

    simpleRepository.save(new SimpleEntity(alwaysSuccessName_InMainService_AfterParallel));
  }
}
