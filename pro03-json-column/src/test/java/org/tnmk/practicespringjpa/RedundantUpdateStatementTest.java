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
import org.tnmk.practicespringjpa.correctimplementation.datafactory.SampleEntityFactory;
import org.tnmk.practicespringjpa.correctimplementation.entity.SampleEntity;
import org.tnmk.practicespringjpa.correctimplementation.story.SampleStory;
import org.tnmk.practicespringjpa.wrongimplementation.datafactory.SampleWithWrongChildEntityFactory;
import org.tnmk.practicespringjpa.wrongimplementation.entity.SampleWithWrongChildEntity;
import org.tnmk.practicespringjpa.wrongimplementation.story.SampleWithWrongChildStory;

import java.util.Optional;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {JsonColumnApplication.class})
@ContextConfiguration(initializers = EmbeddedDBContextInitializer.class)
public class RedundantUpdateStatementTest {
    @Autowired
    private SampleStory sampleStory;

    @Autowired
    private SampleWithWrongChildStory sampleWithWrongChildStory;

    @Test
    public void test_ChildEntity_DO_NOT_ExecutingRedundantUpdateStatement() {
        SampleEntity newSampleEntity = SampleEntityFactory.constructSampleEntity();

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
        SampleWithWrongChildEntity newSampleWithWrongChildEntity = SampleWithWrongChildEntityFactory.constructSampleEntity();

        SampleWithWrongChildEntity savedNewSampleWithWrongChildEntity = sampleWithWrongChildStory.create(newSampleWithWrongChildEntity);
        /** WRONG BEHAVIOR: The updatedDateTime will change after create(), it means there's a redundant `update` statement was executed.*/
        Assert.assertTrue(savedNewSampleWithWrongChildEntity.getUpdateDateTime().isAfter(savedNewSampleWithWrongChildEntity.getCreatedDateTime()));

        Optional<SampleWithWrongChildEntity> foundSampleEntity = sampleWithWrongChildStory.findById(savedNewSampleWithWrongChildEntity.getSampleEntityId());
        /** NOTE: no redundant update is executed after findById()*/
        Assert.assertEquals(
                foundSampleEntity.get().getUpdateDateTime(),
                savedNewSampleWithWrongChildEntity.getUpdateDateTime()
        );
    }
}
