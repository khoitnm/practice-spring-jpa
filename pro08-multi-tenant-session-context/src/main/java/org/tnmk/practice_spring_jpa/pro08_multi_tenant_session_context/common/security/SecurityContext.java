package org.tnmk.practice_spring_jpa.pro08_multi_tenant_session_context.common.security;

import jakarta.annotation.Nullable;

public class SecurityContext {
    public static final ScopedValue<String> TENANT_ID = ScopedValue.newInstance();

    public static @Nullable String getTenantId() {
        return TENANT_ID.orElse(null);
    }

    public static void runInTenantContext(String tenantId, Runnable function) {
        ScopedValue.where(SecurityContext.TENANT_ID, tenantId).run(function);
    }
}
