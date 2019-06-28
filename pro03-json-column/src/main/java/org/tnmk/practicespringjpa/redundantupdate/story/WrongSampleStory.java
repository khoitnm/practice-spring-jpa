package org.tnmk.practicespringjpa.redundantupdate.story;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringjpa.redundantupdate.entity.WrongSampleEntity;
import org.tnmk.practicespringjpa.redundantupdate.repository.WrongSampleRepository;

import java.util.Optional;

@Service
public class WrongSampleStory {

    @Autowired
    private WrongSampleRepository wrongSampleRepository;

    public WrongSampleEntity create(WrongSampleEntity wrongSampleEntity) {
        return wrongSampleRepository.save(wrongSampleEntity);
    }

    public Optional<WrongSampleEntity> findById(long id){
        return wrongSampleRepository.findById(id);
    }
}
