package org.tnmk.practicespringjpa;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.practicespringjpa.correctimplementation.datafactory.ChildWithoutMapComparisionEntityFactory;
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
        /* The updatedDateTime doesn't change after create(), it means there's no redundant `update` statement was executed.*/
        Assert.assertEquals(
                savedNewSampleEntity.getCreatedDateTime(),
                savedNewSampleEntity.getUpdateDateTime()
        );

        Optional<SampleEntity> foundSampleEntity = sampleStory.findById(savedNewSampleEntity.getSampleEntityId());
        /* The updatedDateTime doesn't change after findById(), it means there's no redundant `update` statement was executed.*/
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
        /* No redundant update is executed after findById()*/
        Assert.assertEquals(
                foundSampleEntity.get().getUpdateDateTime(),
                savedNewSampleEntity.getUpdateDateTime()
        );
    }

    @Test
    public void test_Hibernate_ignore_update_statement_even_though_we_updated_the_Map() {
        SampleEntity newSampleEntity = SampleEntityFactory.constructSampleEntityWithChildren();
        newSampleEntity.setChildWithoutMapComparisionEntity(ChildWithoutMapComparisionEntityFactory.constructChildEntity());

        SampleEntity savedNewSampleEntity = sampleStory.create(newSampleEntity);
        Assert.assertEquals(
                savedNewSampleEntity.getCreatedDateTime(),
                savedNewSampleEntity.getUpdateDateTime()
        );

        Optional<SampleEntity> foundSampleEntity = sampleStory.findById(savedNewSampleEntity.getSampleEntityId());
        /* No redundant update is executed after findById()*/
        Assert.assertEquals(
                foundSampleEntity.get().getUpdateDateTime(),
                savedNewSampleEntity.getUpdateDateTime()
        );

        SampleEntity savedUpdateSampleEntity = sampleStory.update(savedNewSampleEntity);
        /* This assert means that Hibernate didn't trigger any update statement to DB because the data is still exactly the same */
        Assert.assertEquals(
                savedNewSampleEntity.getUpdateDateTime(),
                savedUpdateSampleEntity.getUpdateDateTime()
        );

        /** This assert means that even though we update the Map, Hibernate still does NOT update entity because the {@link ChildWithWrongMapComparisionEntity#equals()} ignored that Map*/
        savedUpdateSampleEntity.getChildWithoutMapComparisionEntity().getCharacteristics().put("newCharacteristic", "excellent_" + System.nanoTime());
        SampleEntity savedUpdatedMapInSampleEntity = sampleStory.update(savedUpdateSampleEntity);
        Assert.assertEquals(savedUpdateSampleEntity.getUpdateDateTime(), savedUpdatedMapInSampleEntity.getUpdateDateTime());
        foundSampleEntity = sampleStory.findById(savedUpdatedMapInSampleEntity.getSampleEntityId());
        Assert.assertTrue(foundSampleEntity.get().getChildWithoutMapComparisionEntity().getCharacteristics().isEmpty());

    }
}
