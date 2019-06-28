package org.tnmk.practicespringjpa.samplebusiness.story;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringjpa.samplebusiness.entity.ParentEntity;
import org.tnmk.practicespringjpa.samplebusiness.repository.SampleRepository;

import java.util.Optional;

@Service
public class SampleStory {

    @Autowired
    private SampleRepository sampleRepository;

    public ParentEntity create(ParentEntity parentEntity) {
        return sampleRepository.save(parentEntity);
    }

    public Optional<ParentEntity> findById(long id){
        return sampleRepository.findById(id);
    }
}
