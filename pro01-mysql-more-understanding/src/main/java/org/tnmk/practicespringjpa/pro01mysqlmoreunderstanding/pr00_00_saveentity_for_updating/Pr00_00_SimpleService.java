package org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr00_00_saveentity_for_updating;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.common.SimpleEntity;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class Pr00_00_SimpleService {

  private final Pr00_00_SimpleRepository simpleRepository;

  @Transactional
  public SimpleEntity saveEntities(SimpleEntity entity) {
    if (entity.getName() == null) {
      throw new IllegalStateException("name cannot be null");
    }
    return simpleRepository.save(entity);
  }
}
