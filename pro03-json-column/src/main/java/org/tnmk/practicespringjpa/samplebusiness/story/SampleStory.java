package org.tnmk.practicespringjpa.samplebusiness.story;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringjpa.samplebusiness.entity.ChildEntity;
import org.tnmk.practicespringjpa.samplebusiness.entity.SampleEntity;
import org.tnmk.practicespringjpa.samplebusiness.repository.SampleRepository;

import java.util.Arrays;

@Service
public class SampleStory {

    @Autowired
    private SampleRepository sampleRepository;

    public SampleEntity createSample() {
        SampleEntity sampleEntity = new SampleEntity();
        sampleEntity.setName("Sample_" + System.nanoTime());
        sampleEntity.setMainChildEntity(constructChildEntity());
        sampleEntity.setOtherChildEntities(Arrays.asList(constructChildEntity(), constructChildEntity()));

        //FIXME when creating a single entity, Hibernate execute 2 queries: insert & update.
        // The reason is JsonConverter, which makes comparision is different
        // Fix: We should implement equals() and hashCode() for entities.
        return sampleRepository.save(sampleEntity);
    }

    private ChildEntity constructChildEntity() {
        ChildEntity childEntity = new ChildEntity();
        childEntity.setName("Child_" + System.nanoTime());
        childEntity.setDescription("Description_" + System.nanoTime());
        return childEntity;
    }
}
