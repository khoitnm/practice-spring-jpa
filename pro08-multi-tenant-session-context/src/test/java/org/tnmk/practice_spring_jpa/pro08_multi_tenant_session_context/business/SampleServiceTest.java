package org.tnmk.practice_spring_jpa.pro08_multi_tenant_session_context.business;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.CannotCreateTransactionException;
import org.tnmk.practice_spring_jpa.pro08_multi_tenant_session_context.common.security.SecurityContext;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class SampleServiceTest {

    @Autowired
    private SampleService sampleService;

    @Test
    void testCreateAndFindEntity() {
        // Tenant 1 can find the entity created by the same tenant.
        String entityName = "Test description";
        AtomicReference<SampleEntity> created = new AtomicReference<>();
        SecurityContext.runInTenantContext("SampleServiceTenant-01", () -> {
            SampleEntity entity = sampleService.createEntity(entityName);
            created.set(entity);
            assertThat(entity.getId()).isNotNull();
            assertThat(entity.getName()).isEqualTo(entityName);
        });

        SecurityContext.runInTenantContext("SampleServiceTenant-01", () -> {
            Optional<SampleEntity> found = sampleService.findById(created.get().getId());
            assertThat(found).isPresent();
            assertThat(found.get().getName()).isEqualTo(entityName);
        });

        // Tenant 2 won't be able to find the entity created by Tenant 1.
        SecurityContext.runInTenantContext("SampleServiceTenant-02", () -> {
            Optional<SampleEntity> found2 = sampleService.findById(created.get().getId());
            assertThat(found2).isEmpty();
        });

        // If we don't set tenant, won't be able to find the entity created by Tenant 1.
        SecurityContext.runInTenantContext(null, () -> {
            Assertions.assertThatThrownBy(() ->
                sampleService.findById(created.get().getId())
            ).isInstanceOf(CannotCreateTransactionException.class);// The exception type here could be changed based on the Spring versions.
        });
    }

    @Test
    void testCreateEntityWithoutTenantIdThrowsException() {
        SecurityContext.runInTenantContext(null, () -> { // This null tenant will make the business logic not have tenant, and throw exception.
            Assertions.assertThatThrownBy(() ->
                sampleService.createEntity("Some entity")

            ).isInstanceOf(CannotCreateTransactionException.class);// The exception type here could be changed based on the Spring versions.
        });

    }
}
