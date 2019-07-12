package org.tnmk.practicespringjpa.pro04flyway.sample.story;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringjpa.pro04flyway.sample.entity.SampleEntity;
import org.tnmk.practicespringjpa.pro04flyway.sample.repository.SampleRepository;

import javax.transaction.Transactional;


@Transactional // Without this, the service cannot commit transaction, hence cannot create/update/delete items
@Service
public class SampleStory {
    @Autowired
    private SampleRepository sampleRepository;

    public SampleEntity create(SampleEntity sampleEntity) {
        return sampleRepository.save(sampleEntity);
    }
}
