package org.tnmk.practicespringjpa.pro00mssql01jdbcbatch.sample;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This method is similar, but a bit different from UPSERT:
 * - UPSERT means: if data exist, update. Otherwise, insert.
 * - SELECTSERT means: if data exist, return data. Otherwise, insert.
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class SampleJdbcRepository_SelectSert {
  private final DataSource dataSource;

  /**
   * @param entityName
   * @return generated entity id.
   */
  /**
   * @param entityName
   * @return generated entity id.
   */
  public long insert(String entityName, String uniqueCode) {
    String sqlInsert = "INSERT INTO sample_entity (name, entity_code)"
        + "VALUES (?, ?, ?);";

    // Column's name here must match the PK name in SampleEntity.
    String generatedColumns[] = { "id" };
    try (
        Connection connection = dataSource.getConnection();
        // We can also use Statement.RETURN_GENERATED_KEYS instead of generatedColumns,
        // but it looks like generatedColumns is a safer option (because it doesn't rely on Jdbc Driver).
        PreparedStatement statement = connection.prepareStatement(sqlInsert, generatedColumns);
    ) {
      statement.setString(1, entityName);
      statement.setString(2, uniqueCode);
      int insertedRows = statement.executeUpdate();
      log.info("Inserted Rows: " + insertedRows);

      try (ResultSet rs = statement.getGeneratedKeys()) {
        if (rs.next()) {
          long id = rs.getLong(1);
          return id;
        } else {
          throw new RuntimeException("Cannot get generated id when inserting entity with name '" + entityName + "'");
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
