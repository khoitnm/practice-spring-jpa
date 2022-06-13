package org.tnmk.practicespringjpa.pro00mssql01jdbcbatch.datafactory;

import org.tnmk.practicespringjpa.pro00mssql01jdbcbatch.entity.SampleEntity;

import java.time.ZonedDateTime;

public class SampleEntityFactory {
  public static SampleEntity constructSampleEntity() {
    SampleEntity sampleEntity = new SampleEntity();
    sampleEntity.setName("Sample_" + System.nanoTime());
    sampleEntity.setStartingDateTime(ZonedDateTime.now());
    return sampleEntity;
  }
}
