package org.tnmk.practicespringjpa.pro07multitenant.jsoncolumn;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.practicespringjpa.BaseSpringTest;
import org.tnmk.practicespringjpa.pro07multitenant.datafactory.SampleEntityFactory;
import org.tnmk.practicespringjpa.pro07multitenant.entity.SampleEntity;
import org.tnmk.practicespringjpa.pro07multitenant.story.SampleStory;

import java.util.Optional;

public class CreateAndRetrieveJSONColumnTest extends BaseSpringTest {
    @Autowired
    private SampleStory sampleStory;

    @Test
    public void when_findAnEntity_then_return_createdEntity() {
        SampleEntity newSampleEntity = SampleEntityFactory.constructSampleEntity();
        SampleEntity savedNewSampleEntity = sampleStory.create(newSampleEntity);

        Assert.assertNotNull(savedNewSampleEntity.getId());
        Assert.assertNotNull(savedNewSampleEntity.getCreatedDateTime());
        Assert.assertNotNull(savedNewSampleEntity.getUpdateDateTime());

        Optional<SampleEntity> foundSampleEntity = sampleStory.findById(savedNewSampleEntity.getId());
        Assert.assertTrue(foundSampleEntity.isPresent());
        Assert.assertEquals(
                savedNewSampleEntity.getCreatedDateTime(),
                foundSampleEntity.get().getCreatedDateTime()
        );
    }

}
