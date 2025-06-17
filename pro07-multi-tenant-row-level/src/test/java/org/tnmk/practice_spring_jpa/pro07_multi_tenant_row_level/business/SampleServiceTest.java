package org.tnmk.practice_spring_jpa.pro07_multi_tenant_row_level.business;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.tnmk.practice_spring_jpa.pro07_multi_tenant_row_level.common.security.SecurityContext;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class SampleServiceTest {

    @Autowired
    private SampleService sampleService;

    @Test
    void testCreateAndFindEntity() {
        SecurityContext.setTenantId("SampleService-01");
        SampleEntity created = sampleService.createEntity("Test description");
        assertThat(created.getId()).isNotNull();


        Optional<SampleEntity> found = sampleService.findById(created.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Test description");

        SecurityContext.setTenantId("SampleService-02");
        Optional<SampleEntity> found2 = sampleService.findById(created.getId());
        assertThat(found2).isEmpty();
    }
}
