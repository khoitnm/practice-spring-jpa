package org.tnmk.practicespringjpa;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.tnmk.practicespringjpa.common.embeddeddb.EmbeddedDBContextInitializer;
import org.tnmk.practicespringjpa.redundantupdate.datafactory.WrongSampleEntityFactory;
import org.tnmk.practicespringjpa.redundantupdate.entity.WrongSampleEntity;
import org.tnmk.practicespringjpa.redundantupdate.story.WrongSampleStory;

import java.util.Optional;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {JsonColumnApplication.class})
@ContextConfiguration(initializers = EmbeddedDBContextInitializer.class)
public class RedundantUpdateStatementTest {
    @Autowired
    private WrongSampleStory wrongSampleStory;

    @Test
    public void test_CanCreateAndRetrieveData_But_ExecutingRedundantUpdateStatement() {
        WrongSampleEntity newSampleEntity = WrongSampleEntityFactory.constructSampleEntity();
        WrongSampleEntity savedNewSampleEntity = wrongSampleStory.create(newSampleEntity);

        Assert.assertNotNull(savedNewSampleEntity.getSampleEntityId());
        Assert.assertNotNull(savedNewSampleEntity.getCreatedDateTime());
        Assert.assertNotNull(savedNewSampleEntity.getUpdateDateTime());
        //The updatedDateTime will change after create(), it means there's a redundant `update` statement was executed.
        Assert.assertTrue(savedNewSampleEntity.getUpdateDateTime().isAfter(savedNewSampleEntity.getCreatedDateTime()));

        Optional<WrongSampleEntity> foundSampleEntity = wrongSampleStory.findById(savedNewSampleEntity.getSampleEntityId());
        Assert.assertTrue(foundSampleEntity.isPresent());
        Assert.assertEquals(savedNewSampleEntity.getCreatedDateTime(), foundSampleEntity.get().getCreatedDateTime());
        //The updatedDateTime will change after findById(), it means there's a redundant `update` statement was executed.
        Assert.assertTrue(foundSampleEntity.get().getUpdateDateTime().isAfter(savedNewSampleEntity.getCreatedDateTime()));

    }
}
