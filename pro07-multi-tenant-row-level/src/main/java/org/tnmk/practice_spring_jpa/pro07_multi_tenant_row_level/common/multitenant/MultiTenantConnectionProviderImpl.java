package org.tnmk.practice_spring_jpa.pro07_multi_tenant_row_level.common.multitenant;

import org.hibernate.dialect.Dialect;
import org.hibernate.engine.jdbc.connections.spi.DatabaseConnectionInfo;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.owasp.esapi.PreparedString;
import org.owasp.esapi.codecs.MySQLCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.lang.invoke.MethodHandles;
import java.sql.*;

@Component
public class MultiTenantConnectionProviderImpl implements MultiTenantConnectionProvider<String> {
    private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

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
    public Connection getConnection(String tenantId) throws SQLException {
        Connection conn = dataSource.getConnection();
        if (StringUtils.hasText(tenantId)) {
            if (!tenantService.tenantExists(conn, tenantId)) {
                tenantService.createTenant(conn, tenantId);
            }
            setUser(conn, tenantId);
            return conn;
        } else {
            logger.trace("There's no tenantIdentifier ({}), hence just return a null connection.", tenantId);
            return null;
        }
    }

    private void setUser(Connection connection, String tenantId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("EXECUTE AS USER = ?;")) {
            statement.setString(1, tenantId);
            statement.execute();
        } finally {
            logger.trace("Set tenantId {} to the connection successfully", tenantId);
        }
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        // If we just close connection without REVERT the user context, that thread will be put back into DB Connection Pool while keeping user context.
        // So when the next thread, that thread can borrow this connection, and try to run `CREATE USER [?] WITHOUT LOGIN;`, which will get error because it's being in the previous user context.:
        //  com.microsoft.sqlserver.jdbc.SQLServerException: User does not have permission to perform this action.
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
