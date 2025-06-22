package org.tnmk.practice_spring_jpa.pro08_multi_tenant_session_context.business;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SampleService {

    private final SampleRepository sampleRepository;

    public SampleEntity createEntity(String description) {
        SampleEntity entity = new SampleEntity();
        entity.setName(description);

        // Different from pro07-multi-tenant-row-level, this approach requires the organizationId to be set manually here.
        // because SQL doesn't support getting value from session context to set default value for a column.
        // However, we can use EntityListener to automatically set the organizationId when the entity is persisted.
        // So I commented out the line below.
        // entity.setOrganizationId(SecurityContext.getTenantId());
        
        return sampleRepository.save(entity);
    }

    @Transactional
    public void errorRollbackTrans(String rollbackEntityName) {
        SampleEntity entity = createEntity(rollbackEntityName);
        throw new RuntimeException("Fake error to test rollback, also check statements in multi-tenant to see whether it's in the same transaction or not: " + entity.getName());
    }

    public Optional<SampleEntity> findById(Long id) {
        return sampleRepository.findById(id);
    }
}
