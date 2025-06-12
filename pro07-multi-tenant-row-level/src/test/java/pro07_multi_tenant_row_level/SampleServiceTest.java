package pro07_multi_tenant_row_level;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pro07_multi_tenant_row_level.business.SampleEntity;
import pro07_multi_tenant_row_level.business.SampleService;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SampleServiceTest {

    @Autowired
    private SampleService sampleService;

    @Test
    void testCreateAndFindEntity() {
        SampleEntity created = sampleService.createEntity("Test description");
        assertThat(created.getId()).isNotNull();

        SampleEntity found = sampleService.findById(created.getId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getDescription()).isEqualTo("Test description");
    }
}
