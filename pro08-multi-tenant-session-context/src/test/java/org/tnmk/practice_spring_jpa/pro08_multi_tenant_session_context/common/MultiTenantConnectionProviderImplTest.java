package org.tnmk.practice_spring_jpa.pro08_multi_tenant_session_context.common;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.tnmk.practice_spring_jpa.pro08_multi_tenant_session_context.common.multitenant.MultiTenantConnectionProviderImpl;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;

@SpringBootTest
class MultiTenantConnectionProviderImplTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private MultiTenantConnectionProviderImpl multiTenantConnectionProvider;

    @Test
    void releaseAnyConnection_whenResetContextFails_thenShouldExecuteCatchBlockLogic() throws SQLException {
        Connection realConnection = dataSource.getConnection();

        try (Connection spiedConnection = spy(realConnection)) {
            // GIVEN:
            // We stub the spied connection to throw an exception ONLY when
            // prepareStatement is called with the SQL from resetTenantContext.
            // NOTE: We use doThrow().when() for spying on methods of real objects.
            doThrow(new SQLException("Test exception on reset context"))
                    .when(spiedConnection)
                    .prepareStatement(MultiTenantConnectionProviderImpl.SQL_RESET_TENANT_CONTEXT);

            // WHEN: Call the method under test with the spy. We expect it to throw the exception we just defined.
            assertThrows(SQLException.class, () -> {
                multiTenantConnectionProvider.releaseAnyConnection(spiedConnection);
            });

            // THEN: Verify that the connection is closed after the exception is thrown.

            Connection physicalConnection = spiedConnection.unwrap(Connection.class);
            assertTrue(physicalConnection.isClosed(), "The physical connection should be closed after the exception is thrown.");
            assertTrue(spiedConnection.isClosed(), "The logical connection should be closed after the exception is thrown.");
        } // After this, spiedConnection will automatically close, and hence the realConnection will also be closed.
    }
}