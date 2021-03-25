package org.tnmk.practicespringjpa.pro07multitenant.jsoncolumn;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.practicespringjpa.BaseSpringTest;
import org.tnmk.practicespringjpa.pro07multitenant.correctimplementation.datafactory.SampleEntityFactory;
import org.tnmk.practicespringjpa.pro07multitenant.correctimplementation.entity.SampleEntity;
import org.tnmk.practicespringjpa.pro07multitenant.correctimplementation.story.SampleStory;

import java.util.Optional;

public class CreateAndRetrieveJSONColumnTest extends BaseSpringTest {
    @Autowired
    private SampleStory sampleStory;

    @Test
    public void test_canCreateAndRetrieveData() {
        SampleEntity newSampleEntity = SampleEntityFactory.constructSampleEntityWithChildren();
        SampleEntity savedNewSampleEntity = sampleStory.create(newSampleEntity);

        Assert.assertNotNull(savedNewSampleEntity.getSampleEntityId());
        Assert.assertNotNull(savedNewSampleEntity.getCreatedDateTime());
        Assert.assertNotNull(savedNewSampleEntity.getUpdateDateTime());

        Optional<SampleEntity> foundSampleEntity = sampleStory.findById(savedNewSampleEntity.getSampleEntityId());
        Assert.assertTrue(foundSampleEntity.isPresent());
        Assert.assertEquals(
                foundSampleEntity.get().getMainChildEntity().getName(),
                newSampleEntity.getMainChildEntity().getName()
        );
        Assert.assertEquals(
                savedNewSampleEntity.getCreatedDateTime(),
                foundSampleEntity.get().getCreatedDateTime()
        );
    }

}
