package org.tnmk.practicespringjpa;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.practicespringjpa.correctimplementation.datafactory.SampleEntityFactory;
import org.tnmk.practicespringjpa.correctimplementation.datafactory.WrongChildEntityFactory;
import org.tnmk.practicespringjpa.correctimplementation.entity.SampleEntity;
import org.tnmk.practicespringjpa.correctimplementation.story.SampleStory;

import java.util.Optional;

public class RedundantUpdateStatementTest extends BaseTest {
    @Autowired
    private SampleStory sampleStory;

    @Test
    public void test_ChildEntity_DO_NOT_ExecutingRedundantUpdateStatement() {
        SampleEntity newSampleEntity = SampleEntityFactory.constructSampleEntityWithChildren();

        SampleEntity savedNewSampleEntity = sampleStory.create(newSampleEntity);
        /** The updatedDateTime doesn't change after create(), it means there's no redundant `update` statement was executed.*/
        Assert.assertEquals(
                savedNewSampleEntity.getCreatedDateTime(),
                savedNewSampleEntity.getUpdateDateTime()
        );

        Optional<SampleEntity> foundSampleEntity = sampleStory.findById(savedNewSampleEntity.getSampleEntityId());
        /** The updatedDateTime doesn't change after findById(), it means there's no redundant `update` statement was executed.*/
        Assert.assertEquals(
                foundSampleEntity.get().getUpdateDateTime(),
                savedNewSampleEntity.getUpdateDateTime()
        );
    }

    @Test
    public void test_WrongChildEntity_DO_ExecutingRedundantUpdateStatement() {
        SampleEntity newSampleEntity = SampleEntityFactory.constructSampleEntityWithChildren();
        /** The below line will cause the wrong behavior */
        newSampleEntity.setWrongChildEntity(WrongChildEntityFactory.constructChildEntity());

        SampleEntity savedNewSampleEntity = sampleStory.create(newSampleEntity);
        /** WRONG BEHAVIOR: The updatedDateTime will change after create(), it means there's a redundant `update` statement was executed.*/
        Assert.assertTrue(savedNewSampleEntity.getUpdateDateTime().isAfter(savedNewSampleEntity.getCreatedDateTime()));

        Optional<SampleEntity> foundSampleEntity = sampleStory.findById(savedNewSampleEntity.getSampleEntityId());
        /** NOTE: no redundant update is executed after findById()*/
        Assert.assertEquals(
                foundSampleEntity.get().getUpdateDateTime(),
                savedNewSampleEntity.getUpdateDateTime()
        );
    }
}
