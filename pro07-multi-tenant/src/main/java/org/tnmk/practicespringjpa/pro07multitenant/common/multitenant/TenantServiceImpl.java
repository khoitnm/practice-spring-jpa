package org.tnmk.practicespringjpa.pro07multitenant.common.multitenant;

import org.owasp.esapi.PreparedString;
import org.owasp.esapi.codecs.MySQLCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Service
public class TenantServiceImpl implements TenantService {
  private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  static final String CREATE_TENANT_LOGIN = ""
      + "CREATE USER [?] WITHOUT LOGIN;"
      + "ALTER ROLE db_datareader ADD MEMBER [?];"
      + "ALTER ROLE db_datawriter ADD MEMBER [?];"
      + "GRANT IMPERSONATE ON USER::[?] TO [?];";

  static final String TENANT_EXISTS = "SELECT DATABASE_PRINCIPAL_ID('?');";

  private final DataSource dataSource;

  public TenantServiceImpl(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public boolean tenantExists(String tenantId) throws SQLException {
    boolean result = false;
    try (Connection connection = dataSource.getConnection()) {
      try (Statement statement = connection.createStatement()) {
        PreparedString tenantExistsQuery = new PreparedString(TENANT_EXISTS, new MySQLCodec(MySQLCodec.Mode.ANSI));
        tenantExistsQuery.set(1, tenantId);
        try (ResultSet resultSet = statement.executeQuery(tenantExistsQuery.toString())) {
          if (resultSet.next()) {
            result = resultSet.getString(1) != null;
          }
        }
      }
    } finally {
      logger.trace("Check TenantId {} exist: {}", tenantId, result);
    }
    return result;
  }

  @Override
  public void createTenant(String tenantId) throws SQLException {
    try (Connection connection = dataSource.getConnection()) {
      try (Statement statement = connection.createStatement()) {
        PreparedString createTenantQuery = new PreparedString(CREATE_TENANT_LOGIN, new MySQLCodec(MySQLCodec.Mode.ANSI));
        createTenantQuery.set(1, tenantId);
        createTenantQuery.set(2, tenantId);
        createTenantQuery.set(3, tenantId);
        createTenantQuery.set(4, tenantId);
        createTenantQuery.set(5, connection.getMetaData().getUserName());
        statement.execute(createTenantQuery.toString());
      }
    } finally {
      logger.trace("Successfully create tenantId {}", tenantId);
    }
  }
}
