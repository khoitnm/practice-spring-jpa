package org.tnmk.practice_spring_jpa.pro08_multi_tenant_session_context.common.multitenant.jpa_auto_set_tenant;

import jakarta.persistence.PrePersist;
import org.tnmk.practice_spring_jpa.pro08_multi_tenant_session_context.business.SampleEntity;
import org.tnmk.practice_spring_jpa.pro08_multi_tenant_session_context.common.security.SecurityContext;

/**
 * This listener automatically sets the organizationId (tenantId) for any entity
 * that implements {@link EntitySupportTenant} and is annotated with @EntityListeners(TenantEntityListener.class).
 * The organizationId is set just before the entity is persisted to the database.
 * <p/>
 * Example usage: {@link SampleEntity}.
 */
public class TenantEntityListener {
    @PrePersist
    public void setTenantIdToEntity(Object entity) {
        if (entity instanceof EntitySupportTenant supportTenantEntity) {
            if (supportTenantEntity.getOrganizationId() == null) {
                supportTenantEntity.setOrganizationId(SecurityContext.getTenantId());
            }
        }
    }
}