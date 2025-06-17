package org.tnmk.practice_spring_jpa.pro07_multi_tenant_row_level.common.multitenant;

import lombok.extern.slf4j.Slf4j;
import org.owasp.esapi.PreparedString;
import org.owasp.esapi.codecs.MySQLCodec;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
@Service
public class TenantService {

    public void createTenantIfNotExist(Connection connection, String tenantId) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            PreparedString createTenantQuery = new PreparedString("""
                IF NOT EXISTS (SELECT DATABASE_PRINCIPAL_ID('?')) BEGIN
                    CREATE USER [?] WITHOUT LOGIN;
                    ALTER ROLE db_datareader ADD MEMBER [?];
                    ALTER ROLE db_datawriter ADD MEMBER [?];
                    GRANT IMPERSONATE ON USER::[?] TO [?];
                END
                """, new MySQLCodec(MySQLCodec.Mode.ANSI)
            );
            createTenantQuery.set(1, tenantId);
            createTenantQuery.set(2, tenantId);
            createTenantQuery.set(3, tenantId);
            createTenantQuery.set(4, tenantId);
            createTenantQuery.set(5, tenantId);
            String dbUsername = connection.getMetaData().getUserName();
            createTenantQuery.set(6, dbUsername);
//            log.debug("dbUsername: {}", dbUsername);
            statement.execute(createTenantQuery.toString());
            log.debug("Created Tenant successfully: {}", tenantId);
        } catch (Exception e) {
            log.error("Create Tenant Failed: {}", tenantId, e);
            throw e;
        }
    }
}
