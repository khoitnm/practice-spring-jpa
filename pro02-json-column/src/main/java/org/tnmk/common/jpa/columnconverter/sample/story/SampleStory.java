package org.tnmk.common.jpa.columnconverter.sample.story;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tnmk.common.jpa.columnconverter.sample.entity.SampleEntity;
import org.tnmk.common.jpa.columnconverter.sample.repository.SampleRepository;

@Service
public class SampleStory {

    @Autowired
    private SampleRepository sampleRepository;

    public SampleEntity createSample(){
        SampleEntity sampleEntity = new SampleEntity();
        sampleEntity.setName("AAA");
        return sampleRepository.save(sampleEntity);
    }
}
