package org.tnmk.practicespringjpa.pro01simpleentity.sample.story;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringjpa.pro01simpleentity.sample.entity.SampleEntity;
import org.tnmk.practicespringjpa.pro01simpleentity.sample.entity.SampleEntityWithUrl;
import org.tnmk.practicespringjpa.pro01simpleentity.sample.repository.SampleJdbcRepostiory;
import org.tnmk.practicespringjpa.pro01simpleentity.sample.repository.SampleRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional // Without this, the service cannot commit transaction, hence cannot create/update/delete items
@Service
public class SampleStory {
  @Autowired
  private SampleRepository sampleRepository;

  @Autowired
  private SampleJdbcRepostiory sampleJdbcRepostiory;

  @EventListener(ApplicationStartedEvent.class)
  public SampleEntity createSample() {
    SampleEntity sampleEntity = new SampleEntity();
    sampleEntity.setName("AAA");
    sampleEntity = sampleRepository.save(sampleEntity);

    findSampleEntitiesWithUrl();

    return sampleEntity;
  }

  public SampleEntity create(SampleEntity sampleEntity) {
    return sampleRepository.save(sampleEntity);
  }

  public List<SampleEntityWithUrl> findSampleEntitiesWithUrl() {
    sampleRepository.findAll(PageRequest.of(0, 10));
    return sampleJdbcRepostiory.findSampleEntitiesWithUrl();
  }
}
