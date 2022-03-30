package org.tnmk.practicespringjpa.pro10transactionsimple.practice_00_simple;

import java.time.ZonedDateTime;

public class SimpleEntityFactory {
  public static SimpleEntity constructSampleEntity() {
    SimpleEntity simpleEntity = new SimpleEntity();
    simpleEntity.setName("Sample_" + System.nanoTime());
    simpleEntity.setStartingDateTime(ZonedDateTime.now());
    return simpleEntity;
  }
}
