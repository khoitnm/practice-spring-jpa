package org.tnmk.practice_spring_jpa.pro07_multi_tenant_row_level.business;

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
        return sampleRepository.save(entity);
    }

    @Transactional
    public void errorRollbackTrans() {
        SampleEntity entity = createEntity("errorEntityThatShouldBeRolledBack");
        throw new RuntimeException("Fake error to test rollback, also check statements in multi-tenant to see whether it's in the same transaction or not: " + entity.getName());
    }

    public Optional<SampleEntity> findById(Long id) {
        return sampleRepository.findById(id);
    }
}
