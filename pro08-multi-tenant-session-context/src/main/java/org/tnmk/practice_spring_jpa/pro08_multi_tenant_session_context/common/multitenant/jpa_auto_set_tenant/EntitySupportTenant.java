package org.tnmk.practice_spring_jpa.pro08_multi_tenant_session_context.common.multitenant.jpa_auto_set_tenant;

public interface EntitySupportTenant {
    void setOrganizationId(String organizationId);

    String getOrganizationId();
}