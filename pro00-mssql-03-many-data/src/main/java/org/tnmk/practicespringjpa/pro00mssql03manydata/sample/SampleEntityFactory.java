package org.tnmk.practicespringjpa.pro00mssql03manydata.sample;

import java.util.UUID;

public class SampleEntityFactory {
  public static SampleEntity constructSampleEntity() {
    SampleEntity sampleEntity = new SampleEntity();
    sampleEntity.setName("Sample_" + System.nanoTime());
    sampleEntity.setEntityCode("C" + UUID.randomUUID());
    return sampleEntity;
  }
}
