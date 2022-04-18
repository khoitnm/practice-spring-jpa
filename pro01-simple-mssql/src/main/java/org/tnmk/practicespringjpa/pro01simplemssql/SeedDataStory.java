package org.tnmk.practicespringjpa.pro01simplemssql;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringjpa.pro01simplemssql.datafactory.SampleEntityFactory;
import org.tnmk.practicespringjpa.pro01simplemssql.entity.SampleEntity;
import org.tnmk.practicespringjpa.pro01simplemssql.story.SampleStory;

@Slf4j
@Service
public class SeedDataStory {

  @Autowired
  private SampleStory sampleStory;

  @EventListener(ApplicationReadyEvent.class)
  public void autoStart() {
    SampleEntity sampleEntity = SampleEntityFactory.constructSampleEntity();
    sampleEntity = sampleStory.create(sampleEntity);
    log.info("SampleEntity: " + sampleEntity);
  }
}
