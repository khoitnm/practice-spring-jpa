package org.tnmk.practicespringjpa.pro00mssql01jdbcbatch.sample.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class SampleBatchJdbcRepository {
  private final DataSource dataSource;

  public List<CreateEntityResult> insert(List<CreateEntityRequest> requests) {
    List<CreateEntityResult> results = new ArrayList<>(requests.size());

    String sqlInsert = "INSERT INTO sample_entity (name, entity_code, starting_date_time)"
        + "VALUES (?, ?, ?);";

    // Column's name here must match the PK name in SampleEntity.
    String generatedColumns[] = { "id" };
    try (
        Connection connection = dataSource.getConnection();
        // We can also use Statement.RETURN_GENERATED_KEYS instead of generatedColumns,
        // but it looks like generatedColumns is a safer option (because it doesn't rely on Jdbc Driver).
        PreparedStatement statement = connection.prepareStatement(sqlInsert, generatedColumns);
    ) {
      for (CreateEntityRequest request : requests) {
        statement.setString(1, request.getName());
        statement.setString(2, request.getEntityCode());
        statement.setDate(3, new Date(Instant.now().toEpochMilli()));
        statement.addBatch();

        // Just TMP
        CreateEntityResult result = new CreateEntityResult(request, 0);// Don't know the id
        results.add(result);
      }
      int[] insertedRows = statement.executeBatch();
      log.info("Inserted Rows: " + insertedRows);

      //      int iRequest = 0;
      //      try (ResultSet rs = statement.getGeneratedKeys()) {
      //        while (rs.next()) {
      //          CreateEntityRequest request = requests.get(iRequest);
      //          long id = rs.getLong(1);
      //          CreateEntityResult result = new CreateEntityResult(request, id);
      //          results.add(result);
      //          iRequest++;
      //        }
      //      }
      return results;
    } catch (BatchUpdateException ex) {
      log.info("Update counts: {}", Arrays.toString(ex.getUpdateCounts()));
      throw new IllegalStateException(ex);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
