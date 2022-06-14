package org.tnmk.practicespringjpa.pro00mssql01jdbcbatch.datafactory;

import org.tnmk.practicespringjpa.pro00mssql01jdbcbatch.entity.SampleEntity;

import java.time.ZonedDateTime;
import java.util.UUID;

public class SampleEntityFactory {
  public static SampleEntity constructSampleEntity() {
    SampleEntity sampleEntity = new SampleEntity();
    sampleEntity.setName("Sample_" + System.nanoTime());
    sampleEntity.setEntityCode("C" + UUID.randomUUID());
    sampleEntity.setStartingDateTime(ZonedDateTime.now());
    return sampleEntity;
  }
}
