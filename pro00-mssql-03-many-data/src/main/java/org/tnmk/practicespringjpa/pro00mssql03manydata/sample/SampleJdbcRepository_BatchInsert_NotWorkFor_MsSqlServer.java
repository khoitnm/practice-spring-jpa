package org.tnmk.practicespringjpa.pro00mssql03manydata.sample;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.tnmk.practicespringjpa.pro00mssql03manydata.sample.CreateEntityRequest;
import org.tnmk.practicespringjpa.pro00mssql03manydata.sample.CreateEntityResult;

import javax.sql.DataSource;
import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class SampleJdbcRepository_BatchInsert_NotWorkFor_MsSqlServer {
  private final DataSource dataSource;

  // FIXME, this approach works with MySQL
  //  However, it doesn't work with MS SQL Server with error:
  //    - When executing `ResultSet rs = statement.getGeneratedKeys()`, it throws an exception:
  //    - "com.microsoft.sqlserver.jdbc.SQLServerException: The statement must be executed before any results can be obtained."
  public List<CreateEntityResult> insert(List<CreateEntityRequest> requests) {
    List<CreateEntityResult> results = new ArrayList<>(requests.size());

    String sqlInsert = "INSERT INTO sample_entity (name, entity_code)"
        + "VALUES (?, ?);";

    // Column's name here must match the PK name in SampleEntity.
    String generatedColumns[] = { "id" };
    try (
        Connection connection = dataSource.getConnection();
        // We can also use Statement.RETURN_GENERATED_KEYS instead of generatedColumns,
        // but it looks like generatedColumns is a safer option (because it doesn't rely on Jdbc Driver).
        PreparedStatement statement = connection.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
    ) {
      for (CreateEntityRequest request : requests) {
        statement.setString(1, request.getName());
        statement.setString(2, request.getEntityCode());
        statement.addBatch();
      }
      int[] insertedRows = statement.executeBatch();
      log.info("Inserted Rows: {}", Arrays.toString(insertedRows));

      int iRequest = 0;
      try (ResultSet rs = statement.getGeneratedKeys()) {
        while (rs.next()) {
          CreateEntityRequest request = requests.get(iRequest);
          long id = rs.getLong(1);
          CreateEntityResult result = new CreateEntityResult(request, id);
          results.add(result);
          iRequest++;
        }
      }
      return results;
    } catch (BatchUpdateException ex) {
      log.info("Update counts: {}", Arrays.toString(ex.getUpdateCounts()));
      throw new IllegalStateException(ex);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
