package org.tnmk.practicespringjpa.pro01simpleentity.sample.story;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringjpa.pro01simpleentity.sample.entity.SampleEntity;
import org.tnmk.practicespringjpa.pro01simpleentity.sample.entity.SampleEntityWithUrl;
import org.tnmk.practicespringjpa.pro01simpleentity.sample.repository.SampleJdbcRepostiory;
import org.tnmk.practicespringjpa.pro01simpleentity.sample.repository.SampleRepository;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class SampleStory {
    @Autowired
    private SampleRepository sampleRepository;

    @Autowired
    private SampleJdbcRepostiory sampleJdbcRepostiory;

    public SampleEntity createSample(){
        SampleEntity sampleEntity = new SampleEntity();
        sampleEntity.setName("AAA");
        return sampleRepository.save(sampleEntity);
    }

    public List<SampleEntityWithUrl> findSampleEntitiesWithUrl(){
        return sampleJdbcRepostiory.findSampleEntitiesWithUrl();
    }
}
