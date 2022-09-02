package org.tnmk.practicespringjpa.pro00mssql02moreunderstanding.story;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringjpa.pro00mssql02moreunderstanding.common.simple.SampleEntity;
import org.tnmk.practicespringjpa.pro00mssql02moreunderstanding.common.simple.SampleRepository;

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
