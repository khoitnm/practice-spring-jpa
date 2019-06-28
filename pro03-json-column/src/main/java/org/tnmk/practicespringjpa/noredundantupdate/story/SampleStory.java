package org.tnmk.practicespringjpa.noredundantupdate.story;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringjpa.noredundantupdate.entity.SampleEntity;
import org.tnmk.practicespringjpa.noredundantupdate.repository.SampleRepository;

import java.util.Optional;

@Service
public class SampleStory {

    @Autowired
    private SampleRepository sampleRepository;

    public SampleEntity create(SampleEntity sampleEntity) {
        return sampleRepository.save(sampleEntity);
    }

    public Optional<SampleEntity> findById(long id){
        return sampleRepository.findById(id);
    }
}
