package org.tnmk.practicespringjpa.pro01simplemysql.sample_business;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class SimpleService {

  private final SimpleRepository simpleRepository;

  @Transactional
  public SimpleEntity saveEntities(SimpleEntity entity) {
    if (entity.getName() == null) {
      throw new IllegalStateException("name cannot be null");
    }
    return simpleRepository.save(entity);
  }
}
