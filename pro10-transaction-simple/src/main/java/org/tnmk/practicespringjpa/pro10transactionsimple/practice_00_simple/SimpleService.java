package org.tnmk.practicespringjpa.pro10transactionsimple.practice_00_simple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SimpleService {

  @Autowired
  private SimpleRepository simpleRepository;

  public SimpleEntity create(SimpleEntity simpleEntity) {
    return simpleRepository.save(simpleEntity);
  }

  public SimpleEntity update(SimpleEntity simpleEntity) {
    if (simpleEntity.getId() == null) {
      throw new IllegalArgumentException("you cannot update an entity with null id.");
    }
    return simpleRepository.save(simpleEntity);
  }

  public Optional<SimpleEntity> findById(long id) {
    return simpleRepository.findById(id);
  }
}
