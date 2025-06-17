package org.tnmk.practice_spring_jpa.pro07_multi_tenant_row_level.support_for_testing.drop_dbusers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DropDbUsersInOneBatchService {

    private final DataSource dataSource;

    // Can add retry or async here if we want: @Retry(name = "DROP_REDUNDANT_TENANTS")
    public void dropUsersInOneBatch(List<String> users) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                for (String username : users) {
                    String dropUserQuery = "DROP USER [" + username + "]";
                    statement.addBatch(dropUserQuery); // Add query to batch
                }
                statement.executeBatch(); // Execute batch
            }
        }
    }
}