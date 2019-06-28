package org.tnmk.practicespringjpa.samplebusiness.story;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringjpa.samplebusiness.entity.SampleEntity;
import org.tnmk.practicespringjpa.samplebusiness.repository.SampleRepository;

import java.util.Optional;

@Service
public class SampleStory {

    @Autowired
    private SampleRepository sampleRepository;

    public SampleEntity create(SampleEntity sampleEntity) {
        //FIXME when creating a single entity, Hibernate execute 2 queries: insert & update.
        // The reason is JsonConverter, which makes comparision is different
        // Fix: We should implement equals() and hashCode() for entities.
        return sampleRepository.save(sampleEntity);
    }

    public Optional<SampleEntity> findById(long id){
        return sampleRepository.findById(id);
    }
}
