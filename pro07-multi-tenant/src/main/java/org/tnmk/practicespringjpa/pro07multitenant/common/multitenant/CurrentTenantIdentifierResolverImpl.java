package org.tnmk.practicespringjpa.pro07multitenant.common.multitenant;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;
import org.tnmk.practicespringjpa.pro07multitenant.common.security.SecurityContext;

@Component
public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {

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
