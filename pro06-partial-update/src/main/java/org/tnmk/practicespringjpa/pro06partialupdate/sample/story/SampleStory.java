package org.tnmk.practicespringjpa.pro06partialupdate.sample.story;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringjpa.pro06partialupdate.sample.entity.SampleEntity;
import org.tnmk.practicespringjpa.pro06partialupdate.sample.entity.SampleEntityWithUrl;
import org.tnmk.practicespringjpa.pro06partialupdate.sample.repository.SampleJdbcRepostiory;
import org.tnmk.practicespringjpa.pro06partialupdate.sample.repository.SampleRepository;

import javax.transaction.Transactional;
import java.util.List;


@Transactional // Without this, the service cannot commit transaction, hence cannot create/update/delete items
@Service
public class SampleStory {
    @Autowired
    private SampleRepository sampleRepository;

    @Autowired
    private SampleJdbcRepostiory sampleJdbcRepostiory;

    public SampleEntity createSample() {
        SampleEntity sampleEntity = new SampleEntity();
        sampleEntity.setName("AAA");
        return sampleRepository.save(sampleEntity);
    }

    public SampleEntity create(SampleEntity sampleEntity) {
        return sampleRepository.save(sampleEntity);
    }

    public List<SampleEntityWithUrl> findSampleEntitiesWithUrl() {
        return sampleJdbcRepostiory.findSampleEntitiesWithUrl();
    }
}
