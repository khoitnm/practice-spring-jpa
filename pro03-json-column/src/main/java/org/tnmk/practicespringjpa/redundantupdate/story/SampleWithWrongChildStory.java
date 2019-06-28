package org.tnmk.practicespringjpa.redundantupdate.story;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringjpa.redundantupdate.entity.SampleWithWrongChildEntity;
import org.tnmk.practicespringjpa.redundantupdate.repository.SampleWithWrongChildRepository;

import java.util.Optional;

@Service
public class SampleWithWrongChildStory {

    @Autowired
    private SampleWithWrongChildRepository sampleWithWrongChildRepository;

    public SampleWithWrongChildEntity create(SampleWithWrongChildEntity sampleWithWrongChildEntity) {
        return sampleWithWrongChildRepository.save(sampleWithWrongChildEntity);
    }

    public Optional<SampleWithWrongChildEntity> findById(long id){
        return sampleWithWrongChildRepository.findById(id);
    }
}
