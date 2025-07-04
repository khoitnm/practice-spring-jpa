package org.tnmk.practice_spring_jpa.pro07_multi_tenant_row_level.common.test_target;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Slf4j
@Service
public class DbUserService {
    public void createDbUser(Connection connection, String tenantId) throws SQLException {
        String sql = String.format("CREATE USER [%s] WITHOUT LOGIN;", tenantId);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.execute();
            log.debug("Created Db User successfully: {}", tenantId);
        } catch (Exception e) {
            log.error("Create Db User failed: {}", tenantId, e);
            throw e;
        }
    }
}
