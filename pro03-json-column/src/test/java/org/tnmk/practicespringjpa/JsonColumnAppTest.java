package org.tnmk.practicespringjpa;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.tnmk.practicespringjpa.samplebusiness.datafactory.SampleEntityFactory;
import org.tnmk.practicespringjpa.samplebusiness.entity.SampleEntity;
import org.tnmk.practicespringjpa.samplebusiness.story.SampleStory;

import java.util.Optional;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {JsonColumnApplication.class})
@ContextConfiguration(initializers = EmbeddedDBContextInitializer.class)
public class JsonColumnAppTest {
    @Autowired
    private SampleStory sampleStory;

    @Test
    public void testStartContext() {
        SampleEntity newSampleEntity = SampleEntityFactory.constructSampleEntity();
        SampleEntity savedNewSampleEntity = sampleStory.create(newSampleEntity);

        Assert.assertNotNull(savedNewSampleEntity.getSampleEntityId());
        Assert.assertNotNull(savedNewSampleEntity.getCreatedDateTime());
        Assert.assertNotNull(savedNewSampleEntity.getUpdateDateTime());
        Assert.assertEquals(savedNewSampleEntity.getUpdateDateTime(), savedNewSampleEntity.getCreatedDateTime());

        Optional<SampleEntity> foundSampleEntity = sampleStory.findById(savedNewSampleEntity.getSampleEntityId());
        Assert.assertTrue(foundSampleEntity.isPresent());
        Assert.assertEquals(savedNewSampleEntity.getCreatedDateTime(), foundSampleEntity.get().getCreatedDateTime());
        Assert.assertEquals(savedNewSampleEntity.getUpdateDateTime(), foundSampleEntity.get().getUpdateDateTime());
    }
}
