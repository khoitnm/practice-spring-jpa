package org.tnmk.practicespringjpa.pro10transactionsimple;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringjpa.pro10transactionsimple.practice_00_simple.SimpleEntityFactory;
import org.tnmk.practicespringjpa.pro10transactionsimple.practice_00_simple.SimpleEntity;
import org.tnmk.practicespringjpa.pro10transactionsimple.practice_00_simple.SimpleService;

@Slf4j
@Service
public class SeedDataStory {

  @Autowired
  private SimpleService simpleService;

  @EventListener(ApplicationReadyEvent.class)
  public void autoStart() {
    SimpleEntity simpleEntity = SimpleEntityFactory.constructSampleEntity();
    simpleEntity = simpleService.create(simpleEntity);
    log.info("SampleEntity: " + simpleEntity);
  }
}
