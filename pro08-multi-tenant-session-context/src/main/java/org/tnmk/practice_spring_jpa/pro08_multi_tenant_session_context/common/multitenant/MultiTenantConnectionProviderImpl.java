package org.tnmk.practice_spring_jpa.pro08_multi_tenant_session_context.common.multitenant;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.jdbc.connections.spi.DatabaseConnectionInfo;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Slf4j
@Component
@RequiredArgsConstructor
public class MultiTenantConnectionProviderImpl implements MultiTenantConnectionProvider<String> {
    private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final DataSource dataSource;

    @Override
    public Connection getAnyConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public Connection getConnection(String tenantId) throws SQLException {
        if (!StringUtils.hasText(tenantId)) {
            throw new IllegalStateException("There's no tenantIdentifier (%s), hence just return a null connection.".formatted(tenantId));
        }
        Connection conn = dataSource.getConnection();
        setTenantToConnection(conn, tenantId);
        return conn;
    }

    private void setTenantToConnection(Connection connection, String tenantId) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("EXEC sp_set_session_context @key=N'tenant_id', @value=?")) {
            ps.setString(1, tenantId);
            ps.execute();
            log.debug("Set tenant context successfully to connection: {}", tenantId);
        } catch (Exception e) {
            log.error("Create tenant failed: {}", tenantId, e);
            throw e;
        }
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        // If we just close connection without REVERT the user context, that thread will be put back into DB Connection Pool while keeping user context.
        // So when the next thread, that thread can borrow this connection, and try to run `CREATE USER [?] WITHOUT LOGIN;`, which will get error because it's being in the previous user context.:
        //  com.microsoft.sqlserver.jdbc.SQLServerException: User does not have permission to perform this action.
        try {
            resetTenantContext(connection);
        } catch (Exception e) {
            logger.error("Failed to remove user context from the connection. Will try to close the connection...", e);
            try {
                // If we just use connection.close(), it won't actually close the physical connection.
                // It will just return the connection to the pool, but it still has the user context set.
                // Then, another thread can borrow this connection and try to run `CREATE USER [?] WITHOUT LOGIN;`,
                // which will get error because it's being in the previous user context.
                // To solve that problem, we need to get the real physical connection (by unwrap logical connection) and close it directly, don't return it to the pool.
                connection.unwrap(Connection.class).close();
            } catch (Exception closeException) {
                logger.error("Failed to close the real (unwrapped) connection. It will at least try to close the wrapped connection.", closeException);
                if (!connection.isClosed()) {
                    connection.close();
                }
            }
            throw e; // Re-throw to ensure the caller is aware of the issue
        }
        if (!connection.isClosed()) {
            connection.close();
        }
    }

    private void resetTenantContext(Connection connection) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("EXEC sp_set_session_context @key=N'tenant_id', @value=NULL")) {
            ps.execute();
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
    public DatabaseConnectionInfo getDatabaseConnectionInfo(Dialect dialect) {
        return MultiTenantConnectionProvider.super.getDatabaseConnectionInfo(dialect);
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
