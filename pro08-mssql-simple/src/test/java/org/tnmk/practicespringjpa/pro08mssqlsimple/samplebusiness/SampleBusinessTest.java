package org.tnmk.practicespringjpa.pro08mssqlsimple.samplebusiness;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.practicespringjpa.pro08mssqlsimple.datafactory.SampleEntityFactory;
import org.tnmk.practicespringjpa.pro08mssqlsimple.entity.SampleEntity;
import org.tnmk.practicespringjpa.pro08mssqlsimple.story.SampleStory;
import org.tnmk.practicespringjpa.pro08mssqlsimple.testinfra.BaseSpringTest;

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
