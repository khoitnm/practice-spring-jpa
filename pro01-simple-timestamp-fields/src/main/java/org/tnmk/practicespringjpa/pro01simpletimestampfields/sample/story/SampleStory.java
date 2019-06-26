package org.tnmk.practicespringjpa.pro01simpletimestampfields.sample.story;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringjpa.pro01simpletimestampfields.sample.entity.SampleEntity;
import org.tnmk.practicespringjpa.pro01simpletimestampfields.sample.repository.SampleRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class SampleStory {
    @Autowired
    private SampleRepository sampleRepository;


    public SampleEntity createSample(){
        SampleEntity sampleEntity = new SampleEntity();
        sampleEntity.setName("AAA");
        return sampleRepository.save(sampleEntity);
    }

    public SampleEntity create(SampleEntity sampleEntity){
        return sampleRepository.save(sampleEntity);
    }

    public Optional<SampleEntity> findById(Long id){
        return sampleRepository.findById(id);
    }

}
