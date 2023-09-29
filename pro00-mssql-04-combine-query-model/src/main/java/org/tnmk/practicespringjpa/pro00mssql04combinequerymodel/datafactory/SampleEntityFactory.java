package org.tnmk.practicespringjpa.pro00mssql04combinequerymodel.datafactory;

import org.tnmk.practicespringjpa.pro00mssql04combinequerymodel.entity.ParentEntity;
import org.tnmk.practicespringjpa.pro00mssql04combinequerymodel.entity.SampleEntity;

import java.time.ZonedDateTime;

public class SampleEntityFactory {
  public static SampleEntity constructSampleEntity(ParentEntity parentEntity) {
    SampleEntity sampleEntity = new SampleEntity();
    sampleEntity.setName("Sample_" + System.nanoTime());
    sampleEntity.setStartingDateTime(ZonedDateTime.now());
    sampleEntity.setParent(parentEntity);
    return sampleEntity;
  }
}
