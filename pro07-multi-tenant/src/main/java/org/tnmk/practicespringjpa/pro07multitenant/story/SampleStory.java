package org.tnmk.practicespringjpa.pro07multitenant.story;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringjpa.pro07multitenant.common.security.SecurityContext;
import org.tnmk.practicespringjpa.pro07multitenant.entity.SampleEntity;
import org.tnmk.practicespringjpa.pro07multitenant.repository.SampleRepository;

import java.util.Optional;

@Service
public class SampleStory {

    @Autowired
    private SampleRepository sampleRepository;

    public SampleEntity createEntity(String tenantId, SampleEntity sampleEntity) {
        SecurityContext.setTenantId(tenantId);
        return sampleRepository.save(sampleEntity);
    }

    public SampleEntity update(SampleEntity sampleEntity) {
        if (sampleEntity.getId() == null){
            throw new IllegalArgumentException("you cannot update an entity with null id.");
        }
        return sampleRepository.save(sampleEntity);
    }

    public Optional<SampleEntity> findById(String tenantId, long id){
        SecurityContext.setTenantId(tenantId);
        return sampleRepository.findById(id);
    }
}
