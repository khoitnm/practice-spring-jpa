package org.tnmk.practicespringjpa.pro09hirakiconnectionpool;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringjpa.pro09hirakiconnectionpool.datafactory.SampleEntityFactory;
import org.tnmk.practicespringjpa.pro09hirakiconnectionpool.entity.SampleEntity;
import org.tnmk.practicespringjpa.pro09hirakiconnectionpool.repository.ConnectionLeakRepository;
import org.tnmk.practicespringjpa.pro09hirakiconnectionpool.story.SampleStory;

@Slf4j
@Service
@RequiredArgsConstructor
public class SeedDataStory {
  public static final int WAIT_TIME_AFTER_OPEN_CONNECTION = 7000;
  private final SampleStory sampleStory;
  private final ConnectionLeakRepository connectionLeakRepository;

  @EventListener(ApplicationReadyEvent.class)
  public void autoStart() throws InterruptedException {
    SampleEntity sampleEntity = SampleEntityFactory.constructSampleEntity();
    sampleEntity = sampleStory.create(sampleEntity);
    log.info("SampleEntity: " + sampleEntity);

    connectionLeakRepository.createConnectionWithoutClosing();
    Thread.sleep(WAIT_TIME_AFTER_OPEN_CONNECTION);
    log.info("After waiting for {} seconds, the connection won't be close.", WAIT_TIME_AFTER_OPEN_CONNECTION);
  }
}
