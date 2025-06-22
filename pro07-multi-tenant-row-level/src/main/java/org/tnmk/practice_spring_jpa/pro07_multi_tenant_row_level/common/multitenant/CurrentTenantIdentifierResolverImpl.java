package org.tnmk.practice_spring_jpa.pro07_multi_tenant_row_level.common.multitenant;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;
import org.tnmk.practice_spring_jpa.pro07_multi_tenant_row_level.common.security.SecurityContext;

@Component
public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver<String> {

    @Override
    public String resolveCurrentTenantIdentifier() {
        final String organizationId = SecurityContext.getTenantId();
        if (organizationId != null) {
            return organizationId;
        }
        return "";
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
