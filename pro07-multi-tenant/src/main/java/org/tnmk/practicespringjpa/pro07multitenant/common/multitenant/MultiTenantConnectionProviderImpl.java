package org.tnmk.practicespringjpa.pro07multitenant.common.multitenant;

import org.apache.commons.lang.StringUtils;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class MultiTenantConnectionProviderImpl implements MultiTenantConnectionProvider {
  private final DataSource dataSource;

  private final TenantService tenantService;

  public MultiTenantConnectionProviderImpl(DataSource dataSource, TenantService tenantService) {
    this.dataSource = dataSource;
    this.tenantService = tenantService;
  }

  @Override
  public Connection getAnyConnection() throws SQLException {
    return dataSource.getConnection();
  }

  @Override
  public Connection getConnection(String tenantIdentifier) throws SQLException {
    if(!StringUtils.isEmpty(tenantIdentifier)) {
      if (!tenantService.tenantExists(tenantIdentifier)) {
        tenantService.createTenant(tenantIdentifier);
      }
      Connection tenantConnection = dataSource.getConnection();
      setUser(tenantConnection, tenantIdentifier);
      return tenantConnection;
    }
    return null;
  }

  private void setUser(Connection connection, String tenantId) throws SQLException {
    try (PreparedStatement statement = connection.prepareStatement("EXECUTE AS USER = ?;")) {
      statement.setString(1, tenantId);
      statement.execute();
    }
  }

  @Override
  public void releaseAnyConnection(Connection connection) throws SQLException {
    removeUser(connection);
    connection.close();
  }

  private void removeUser(Connection connection) throws SQLException {
    try (Statement statement = connection.createStatement()) {
      statement.execute("REVERT;");
    }
  }

  @Override
  public void releaseConnection(String tenantIdentifier, Connection connection)
      throws SQLException {
    releaseAnyConnection(connection);
  }

  @Override
  public boolean supportsAggressiveRelease() {
    return false;
  }

  @Override
  public boolean isUnwrappableAs(Class baseClass) {
    return false;
  }

  @Override
  public <T> T unwrap(Class<T> baseClass) {
    return null;
  }
}
