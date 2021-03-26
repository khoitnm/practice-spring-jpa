package org.tnmk.practicespringjpa.pro07multitenant.story;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringjpa.pro07multitenant.common.security.SecurityContext;
import org.tnmk.practicespringjpa.pro07multitenant.entity.SampleEntity;
import org.tnmk.practicespringjpa.pro07multitenant.repository.SampleRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class SampleStory {

    @Autowired
    private SampleRepository sampleRepository;

    public SampleEntity create(SampleEntity sampleEntity) {
        SecurityContext.setOrganizationId("testTenantId_01");
        return sampleRepository.save(sampleEntity);
    }

    public SampleEntity update(SampleEntity sampleEntity) {
        if (sampleEntity.getId() == null){
            throw new IllegalArgumentException("you cannot update an entity with null id.");
        }
        return sampleRepository.save(sampleEntity);
    }

    public Optional<SampleEntity> findById(long id){
        return sampleRepository.findById(id);
    }
}
