package org.tnmk.practicespringjpa.pro00mssql01jdbcbatch.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
@RequiredArgsConstructor
public class SimpleJdbcRepository {
  private final DataSource dataSource;

  public void insert() {
    String sqlInsert = "";

    // Column's name here must match the PK name in SampleEntity.
    String generatedColumns[] = { "id" };
    try (
        Connection connection = dataSource.getConnection();
        // We can also use Statement.RETURN_GENERATED_KEYS instead of generatedColumns,
        // but it looks like generatedColumns is a safer option (because it doesn't rely on Jdbc Driver).
        PreparedStatement statement = connection.prepareStatement(sqlInsert, generatedColumns);
    ) {
      ResultSet rs = statement.getGeneratedKeys();
      if (rs.next()) {
        long id = rs.getLong(1);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
