package org.tnmk.practicespringjpa.pro10transactionsimple.practice_03_03_parallelstream__privatemethod_hastnx;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SimpleEntity;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SimpleRepository;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
public class Pr03_03_MainService_ParallelStreamWithPrivateMethod_NoCatch {

  private final SimpleRepository simpleRepository;
  private final TransactionTemplate transactionTemplate;

  public Pr03_03_MainService_ParallelStreamWithPrivateMethod_NoCatch(
      SimpleRepository simpleRepository,
      PlatformTransactionManager platformTransactionManager) {

    this.simpleRepository = simpleRepository;
    this.transactionTemplate = new TransactionTemplate(platformTransactionManager);
  }

  /**
   * @param propagationBehavior values from {@link TransactionDefinition}
   * @throws IllegalArgumentException
   */
  @Transactional
  public void saveEntities_WithPrivateMethod_NoCatchEx(
      String alwaysSuccessName_InMainService_BeforeParallel,
      String alwaysSuccessName_InMainService_AfterParallel,
      List<String> parallelEntityNames,
      final int propagationBehavior
  ) throws IllegalArgumentException {
    // Note: when working with transaction, catching exception in main service is different from catching in nested service.

    simpleRepository.save(new SimpleEntity(alwaysSuccessName_InMainService_BeforeParallel));

    parallelEntityNames.stream()
        .parallel() // this is important for this practice: save entities in parallel.
        .forEach(entityName -> {
          transactionTemplate.setPropagationBehavior(propagationBehavior);

          transactionTemplate.execute(status -> {
            log.info("Saving '{}': before starting: Is new trans: {}", entityName, status.isNewTransaction());
            saveNotNullableName(entityName);
            return null;
          });
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
