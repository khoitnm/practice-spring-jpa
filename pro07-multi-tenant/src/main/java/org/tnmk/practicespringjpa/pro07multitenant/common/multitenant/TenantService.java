package org.tnmk.practicespringjpa.pro07multitenant.common.multitenant;

import java.sql.SQLException;

public interface TenantService {
  boolean tenantExists(String tenantId) throws SQLException;
  void createTenant(String tenantId) throws SQLException;
}
