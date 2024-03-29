package org.tnmk.practicespringjpa.pro09hirakiconnectionpool.samplebusiness;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.practicespringjpa.pro09hirakiconnectionpool.datafactory.SampleEntityFactory;
import org.tnmk.practicespringjpa.pro09hirakiconnectionpool.entity.SampleEntity;
import org.tnmk.practicespringjpa.pro09hirakiconnectionpool.story.SampleStory;
import org.tnmk.practicespringjpa.pro09hirakiconnectionpool.testinfra.BaseSpringTest;

import java.util.Optional;

public class SampleBusinessTest extends BaseSpringTest {
  @Autowired
  private SampleStory sampleStory;

  @Test
  public void test() {
    SampleEntity sampleEntity = SampleEntityFactory.constructSampleEntity();
    SampleEntity savedSampleEntity = sampleStory.create(sampleEntity);

    Optional<SampleEntity> sampleEntityOptional = sampleStory.findById(savedSampleEntity.getId());
    Assert.assertTrue(sampleEntityOptional.isPresent());
  }
}
