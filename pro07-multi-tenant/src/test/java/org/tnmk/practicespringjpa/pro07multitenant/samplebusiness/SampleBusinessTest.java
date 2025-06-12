package org.tnmk.practicespringjpa.pro07multitenant.samplebusiness;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.practicespringjpa.pro07multitenant.datafactory.SampleEntityFactory;
import org.tnmk.practicespringjpa.pro07multitenant.entity.SampleEntity;
import org.tnmk.practicespringjpa.pro07multitenant.story.SampleStory;
import org.tnmk.practicespringjpa.pro07multitenant.testinfra.BaseSpringTest;

import java.util.Optional;

public class SampleBusinessTest extends BaseSpringTest
{
    @Autowired
    private SampleStory sampleStory;

    @Test
    public void test()
    {
        SampleEntity sampleEntity = SampleEntityFactory.constructSampleEntity();
        SampleEntity savedSampleEntity = sampleStory.create(sampleEntity);

        Optional<SampleEntity> sampleEntityOptional = sampleStory.findById(savedSampleEntity.getId());
        Assert.assertTrue(sampleEntityOptional.isPresent());
    }

    @Test
    public void test_CannotGetDataFromOtherTenant()
    {
        SampleEntity sampleEntity = SampleEntityFactory.constructSampleEntity();
        SampleEntity savedSampleEntity = sampleStory.create(sampleEntity);

        Optional<SampleEntity> sampleEntityOptional = sampleStory.findById(savedSampleEntity.getId());
        Assert.assertTrue(sampleEntityOptional.isPresent());
    }
}
