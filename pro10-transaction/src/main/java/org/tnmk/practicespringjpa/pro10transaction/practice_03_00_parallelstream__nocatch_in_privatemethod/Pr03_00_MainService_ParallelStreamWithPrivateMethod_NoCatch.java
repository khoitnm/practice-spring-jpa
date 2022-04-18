package org.tnmk.practicespringjpa.pro10transaction.practice_03_00_parallelstream__nocatch_in_privatemethod;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringjpa.pro10transaction.common.SimpleEntity;
import org.tnmk.practicespringjpa.pro10transaction.common.SimpleRepository;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class Pr03_00_MainService_ParallelStreamWithPrivateMethod_NoCatch {

  private final SimpleRepository simpleRepository;

  @Transactional
  public void saveEntities_WithPrivateMethod_NoCatchEx(
      String alwaysSuccessName_InMainService_BeforeParallel,
      String alwaysSuccessName_InMainService_AfterParallel,
      List<String> parallelEntityNames
  ) throws IllegalArgumentException {
    // Note: when working with transaction, catching exception in main service is different from catching in nested service.

    simpleRepository.save(new SimpleEntity(alwaysSuccessName_InMainService_BeforeParallel));

    parallelEntityNames.stream()
        .parallel() // this is important for this practice: save entities in parallel.
        .forEach(entityName -> {
          saveNotNullableName(entityName);
        });

    simpleRepository.save(new SimpleEntity(alwaysSuccessName_InMainService_AfterParallel));
  }

  private void saveNotNullableName(String entityName) throws IllegalArgumentException {
    log.info("Saving '{}': starting ...", entityName);
    if (entityName == null) {
      throw new IllegalArgumentException("name cannot be null");
    }
    SimpleEntity result = simpleRepository.save(new SimpleEntity(entityName));
    log.info("Saving '{}': finished!", entityName);
  }
}
