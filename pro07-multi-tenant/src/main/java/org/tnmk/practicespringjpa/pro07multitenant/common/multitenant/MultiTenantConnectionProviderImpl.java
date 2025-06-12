package org.tnmk.practicespringjpa.pro07multitenant.common.multitenant;

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
import java.sql.Statement;

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
    public Connection getConnection(String tenantIdentifier) throws SQLException {
        if (StringUtils.hasText(tenantIdentifier)) {
            if (!tenantService.tenantExists(tenantIdentifier)) {
                tenantService.createTenant(tenantIdentifier);
            }
            Connection tenantConnection = dataSource.getConnection();
            setUser(tenantConnection, tenantIdentifier);
            return tenantConnection;
        } else {
            logger.trace("There's no tenantIdentifier ({}), hence just return a null connection.", tenantIdentifier);
            return null;
        }
    }

    private void setUser(Connection connection, String tenantId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("EXECUTE AS USER = ?;")) {
            statement.setString(1, tenantId);
            statement.execute();
        } finally {
            logger.trace("Successfully set tenantId {} to the connection `{}`", tenantId, ConnectionLogHelper.toString(connection));
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
