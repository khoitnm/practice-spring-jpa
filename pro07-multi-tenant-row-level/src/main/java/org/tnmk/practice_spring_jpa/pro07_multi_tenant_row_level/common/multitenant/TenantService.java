package org.tnmk.practice_spring_jpa.pro07_multi_tenant_row_level.common.multitenant;

import lombok.extern.slf4j.Slf4j;
import org.owasp.esapi.PreparedString;
import org.owasp.esapi.codecs.MySQLCodec;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
@Service
public class TenantService {

    public boolean tenantExists(Connection connection, String tenantId) throws SQLException {
        boolean result = false;
        try (Statement statement = connection.createStatement()) {
            PreparedString tenantExistsQuery = new PreparedString(
                    "SELECT DATABASE_PRINCIPAL_ID('?');"
                    , new MySQLCodec(MySQLCodec.Mode.ANSI)
            );
            tenantExistsQuery.set(1, tenantId);
            try (ResultSet resultSet = statement.executeQuery(tenantExistsQuery.toString())) {
                if (resultSet.next()) {
                    result = resultSet.getString(1) != null;
                }
            }
        } catch (SQLException | RuntimeException e) {
            log.error("Cannot check tenantExists: {}", tenantId, e);
            throw e;
        }
        return result;
    }

    public void createTenant(Connection connection, String tenantId) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            PreparedString createTenantQuery = new PreparedString("""
                    CREATE USER [?] WITHOUT LOGIN;
                    ALTER ROLE db_datareader ADD MEMBER [?];
                    ALTER ROLE db_datawriter ADD MEMBER [?];
                    GRANT IMPERSONATE ON USER::[?] TO [?];
                    """, new MySQLCodec(MySQLCodec.Mode.ANSI)
            );
            createTenantQuery.set(1, tenantId);
            createTenantQuery.set(2, tenantId);
            createTenantQuery.set(3, tenantId);
            createTenantQuery.set(4, tenantId);
            createTenantQuery.set(5, connection.getMetaData().getUserName());
            statement.execute(createTenantQuery.toString());
        } catch (RuntimeException e) {
            log.error("Create Tenant Failed: {}", tenantId, e);
            throw e;
        }
    }
}
