package org.tnmk.practice_spring_jpa.pro07_multi_tenant_row_level.common.security;

import org.slf4j.MDC;

public class SecurityContext {
    /**
     * Each organization is a tenant, so tenantId is the same as orgId.
     */
    private static final String LOCAL_THREAD_KEY_ORG_ID = "orgId";

    public static String getTenantId() {
        //TODO In the real project, we should use another dedicated ThreadLocal for SecurityContext
        //    , don't use MDC which is dedicated for logging only.
        return MDC.get(LOCAL_THREAD_KEY_ORG_ID);
    }

    public static void setTenantId(String organizationId) {
        MDC.put(LOCAL_THREAD_KEY_ORG_ID, organizationId);
    }
}
