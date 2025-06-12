package org.tnmk.practicespringjpa.pro07multitenant.common.multitenant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
@Service
public class TenantService {

    private final DataSource dataSource;

    public TenantService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean tenantExists(String tenantId) throws SQLException {
        boolean result = false;
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT DATABASE_PRINCIPAL_ID('?');")) {
                preparedStatement.setString(1, tenantId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        result = resultSet.getString(1) != null;
                    }
                }
            }
        } finally {
            log.trace("Check TenantId {} exist: {}", tenantId, result);
        }
        return result;
    }

    public void createTenant(String tenantId) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            log.trace("Connection client info: {}", ConnectionLogHelper.toString(connection));
            try (PreparedStatement preparedStatement = connection.prepareStatement("""
                    \
                    CREATE USER [?] WITHOUT LOGIN;\
                    ALTER ROLE db_datareader ADD MEMBER [?];\
                    ALTER ROLE db_datawriter ADD MEMBER [?];\
                    GRANT IMPERSONATE ON USER::[?] TO [?];""")) {
                preparedStatement.setString(1, tenantId);
                preparedStatement.setString(2, tenantId);
                preparedStatement.setString(3, tenantId);
                preparedStatement.setString(4, tenantId);
                preparedStatement.setString(5, connection.getMetaData().getUserName());
                preparedStatement.execute();
            }
        } finally {
            log.trace("Finish creating tenantId (if there's no error, it means success: {}", tenantId);
        }
    }
}
