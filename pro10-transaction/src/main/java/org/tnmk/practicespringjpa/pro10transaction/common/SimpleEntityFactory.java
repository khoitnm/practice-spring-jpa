package org.tnmk.practicespringjpa.pro10transaction.common;

import java.util.UUID;

public class SimpleEntityFactory {
  public static SimpleEntity constructSampleEntity() {
    SimpleEntity simpleEntity = new SimpleEntity();
    simpleEntity.setName("Sample_" + UUID.randomUUID());
    return simpleEntity;
  }
}
