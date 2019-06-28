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
import org.tnmk.practicespringjpa.samplebusiness.entity.ParentEntity;
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
    public void test_CanCreateAndRetrieveData_WithoutExecutingRedundantUpdateStatement() {
        ParentEntity newParentEntity = SampleEntityFactory.constructSampleEntity();
        ParentEntity savedNewParentEntity = sampleStory.create(newParentEntity);

        Assert.assertNotNull(savedNewParentEntity.getSampleEntityId());
        Assert.assertNotNull(savedNewParentEntity.getCreatedDateTime());
        Assert.assertNotNull(savedNewParentEntity.getUpdateDateTime());
        //The updatedDateTime doesn't change after create(), it means there's no redundant `update` statement was executed.
        Assert.assertEquals(savedNewParentEntity.getUpdateDateTime(), savedNewParentEntity.getCreatedDateTime());

        Optional<ParentEntity> foundSampleEntity = sampleStory.findById(savedNewParentEntity.getSampleEntityId());
        Assert.assertTrue(foundSampleEntity.isPresent());
        Assert.assertEquals(savedNewParentEntity.getCreatedDateTime(), foundSampleEntity.get().getCreatedDateTime());
        //The updatedDateTime doesn't change after findById(), it means there's no redundant `update` statement was executed.
        Assert.assertEquals(savedNewParentEntity.getUpdateDateTime(), foundSampleEntity.get().getUpdateDateTime());
    }
}
