package org.tnmk.practice_spring_jpa.pro07_multi_tenant_row_level.support_for_testing;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A class that help us to query data across many organizations conveniently.
 */
@Component
@AllArgsConstructor
public class SimpleJdbcRepositoryHelper
{
  private final DataSource dataSource;

  public long insert(String sql, Object... arguments) {
    try (
      Connection connection = dataSource.getConnection();
      PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    ) {
      for (int i = 0; i < arguments.length; i++) {
        Object arg = arguments[i];
        statement.setObject(i + 1, arg);
      }

      statement.executeUpdate();

      try (ResultSet rs = statement.getGeneratedKeys()) {
        if (rs.next()) {
          long id = rs.getLong(1);
          return id;
        } else {
          throw new IllegalStateException("Cannot get generated id after inserting data: " + arguments);
        }
      }
    } catch (SQLException e) {
      throw new IllegalStateException(e);
    }
  }

  public <T> List<T> findMany(RowMapper<T> resultSetMapper, String sql, Object... arguments) {
    try (
      Connection connection = dataSource.getConnection();
      PreparedStatement statement = connection.prepareStatement(sql);
    ) {

      for (int i = 0; i < arguments.length; i++) {
        Object arg = arguments[i];
        statement.setObject(i + 1, arg);
      }
      try (ResultSet rs = statement.executeQuery()) {
        List<T> results = new ArrayList<>();
        int rowNumber = 0;
        while (rs.next()) {
          T result = resultSetMapper.mapRow(rs, rowNumber);
          results.add(result);
          rowNumber++;
        }
        return results;
      }
    } catch (SQLException e) {
      throw new IllegalStateException(e);
    }
  }

  public <T> Optional<T> findOne(RowMapper<T> resultSetMapper, String sql, Object... arguments) {
    List<T> results = findMany(resultSetMapper, sql, arguments);
    if (results.isEmpty()) {
      return Optional.empty();
    } else {
      return Optional.of(results.get(0));
    }
  }

  /**
   * @param sql       this could be insert/update/or delete statement.
   * @param arguments
   * @return the number of affected rows
   */
  public int execute(String sql, Object... arguments) {
    try (
      Connection connection = dataSource.getConnection();
      PreparedStatement statement = connection.prepareStatement(sql);
    ) {
      for (int i = 0; i < arguments.length; i++) {
        Object arg = arguments[i];
        statement.setObject(i + 1, arg);
      }
      return statement.executeUpdate();
    } catch (SQLException e) {
      throw new IllegalStateException(e);
    }
  }
}
