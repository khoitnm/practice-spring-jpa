package org.tnmk.practicespringjpa.pro07multitenant.common.security;

import org.slf4j.MDC;

import java.util.UUID;

public class SecurityContext {
  private static final String LOCAL_THREAD_KEY_ORG_ID = "orgId";
  public static String getOrganizationId(){
    //TODO In the real project, we should use another dedicated ThreadLocal for SecurityContext
    //    , don't use MDC which is dedicated for logging only.
    return MDC.get(LOCAL_THREAD_KEY_ORG_ID);
  }

  public static void setOrganizationId(String organizationId) {
    MDC.put(LOCAL_THREAD_KEY_ORG_ID, organizationId);
  }
}
