package pro07_multi_tenant_row_level.business;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SampleService {

    private final SampleRepository sampleRepository;

    public SampleEntity createEntity(String description) {
        SampleEntity entity = new SampleEntity();
        entity.setDescription(description);
        return sampleRepository.save(entity);
    }

    public Optional<SampleEntity> findById(Long id) {
        return sampleRepository.findById(id);
    }
}
