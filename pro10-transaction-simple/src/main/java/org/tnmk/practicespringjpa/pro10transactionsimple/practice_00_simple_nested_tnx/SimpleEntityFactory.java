package org.tnmk.practicespringjpa.pro10transactionsimple.practice_00_simple_nested_tnx;

import java.util.UUID;

public class SimpleEntityFactory {
  public static SimpleEntity constructSampleEntity() {
    SimpleEntity simpleEntity = new SimpleEntity();
    simpleEntity.setName("Sample_" + UUID.randomUUID());
    return simpleEntity;
  }
}
