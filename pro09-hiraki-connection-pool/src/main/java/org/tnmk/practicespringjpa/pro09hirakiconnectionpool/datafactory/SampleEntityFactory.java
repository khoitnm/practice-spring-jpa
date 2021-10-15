package org.tnmk.practicespringjpa.pro09hirakiconnectionpool.datafactory;

import org.tnmk.practicespringjpa.pro09hirakiconnectionpool.entity.SampleEntity;

import java.time.ZonedDateTime;

public class SampleEntityFactory {
  public static SampleEntity constructSampleEntity() {
    SampleEntity sampleEntity = new SampleEntity();
    sampleEntity.setName("Sample_" + System.nanoTime());
    sampleEntity.setStartingDateTime(ZonedDateTime.now());
    return sampleEntity;
  }
}
