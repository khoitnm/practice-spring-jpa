package org.tnmk.practicespringjpa.pro00mssql02moreunderstanding.common.simple;

import java.time.ZonedDateTime;

public class SampleEntityFactory {
  public static SampleEntity constructSampleEntity() {
    SampleEntity sampleEntity = new SampleEntity();
    sampleEntity.setName("Sample_" + System.nanoTime());
    sampleEntity.setStartingDateTime(ZonedDateTime.now());
    return sampleEntity;
  }
}
