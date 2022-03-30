package org.tnmk.practicespringjpa.pro10transactionsimple.story;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringjpa.pro10transactionsimple.entity.SampleEntity;
import org.tnmk.practicespringjpa.pro10transactionsimple.repository.SampleRepository;

import java.util.Optional;

@Service
public class SampleStory {

  @Autowired
  private SampleRepository sampleRepository;

  public SampleEntity create(SampleEntity sampleEntity) {
    return sampleRepository.save(sampleEntity);
  }

  public SampleEntity update(SampleEntity sampleEntity) {
    if (sampleEntity.getId() == null) {
      throw new IllegalArgumentException("you cannot update an entity with null id.");
    }
    return sampleRepository.save(sampleEntity);
  }

  public Optional<SampleEntity> findById(long id) {
    return sampleRepository.findById(id);
  }
}
