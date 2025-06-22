package org.tnmk.practice_spring_jpa.pro08_multi_tenant_session_context.common.multitenant;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;
import org.tnmk.practice_spring_jpa.pro08_multi_tenant_session_context.common.security.SecurityContext;

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
