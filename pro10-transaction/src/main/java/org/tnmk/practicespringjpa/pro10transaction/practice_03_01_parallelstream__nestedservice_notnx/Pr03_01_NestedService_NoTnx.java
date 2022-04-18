package org.tnmk.practicespringjpa.pro10transaction.practice_03_01_parallelstream__nestedservice_notnx;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringjpa.pro10transaction.common.SimpleEntity;
import org.tnmk.practicespringjpa.pro10transaction.common.SimpleRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class Pr03_01_NestedService_NoTnx {
  private final SimpleRepository simpleRepository;

  public void save(String entityName) throws IllegalArgumentException {
    log.info("Saving '{}': starting ...", entityName);
    if (entityName == null) {
      throw new IllegalArgumentException("name cannot be null");
    }
    SimpleEntity result = simpleRepository.save(new SimpleEntity(entityName));
    log.info("Saving '{}': finished!", entityName);
  }
}