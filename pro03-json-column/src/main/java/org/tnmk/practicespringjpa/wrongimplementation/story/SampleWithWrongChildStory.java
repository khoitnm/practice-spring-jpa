package org.tnmk.practicespringjpa.wrongimplementation.story;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringjpa.wrongimplementation.entity.SampleWithWrongChildEntity;
import org.tnmk.practicespringjpa.wrongimplementation.repository.SampleWithWrongChildRepository;

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
