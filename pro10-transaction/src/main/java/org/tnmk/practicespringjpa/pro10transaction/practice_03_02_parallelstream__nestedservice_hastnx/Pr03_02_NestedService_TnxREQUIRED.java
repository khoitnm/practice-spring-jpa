package org.tnmk.practicespringjpa.pro10transaction.practice_03_02_parallelstream__nestedservice_hastnx;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.tnmk.practicespringjpa.pro10transaction.common.SimpleEntity;
import org.tnmk.practicespringjpa.pro10transaction.common.SimpleRepository;

import javax.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class Pr03_02_NestedService_TnxREQUIRED {
  private final SimpleRepository simpleRepository;

  @Transactional(Transactional.TxType.REQUIRED) // this is the default type of transaction.
  public void save_withTnxRequired(String entityName) throws IllegalArgumentException {
    log.info("Saving '{}': starting ...", entityName);
    log.info("transaction {}", TransactionSynchronizationManager.getCurrentTransactionName());
    if (entityName == null) {
      throw new IllegalArgumentException("name cannot be null");
    }
    SimpleEntity result = simpleRepository.save(new SimpleEntity(entityName));
    log.info("Saving '{}': finished!", entityName);
  }
}