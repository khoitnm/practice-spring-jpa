package org.tnmk.practicespringjpa.pro00mssql03manydata.sample;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.stereotype.Repository;
import org.springframework.util.StopWatch;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@Repository
@RequiredArgsConstructor
public class SampleBatchUpdateRepository {
  private static final String UPDATE_SQL = "update sample_entity set name = ? where id = ?";
  private static final int BATCH_SIZE = 1000;
  private final JdbcTemplate jdbcTemplate;

  public void updateNamesForEntities_Approach01(List<SampleEntity> sampleEntities) {
    StopWatch stopWatch = new StopWatch();
    stopWatch.start();

    ParameterizedPreparedStatementSetter<SampleEntity> statementSetter =
        (ps, argument) -> {
          ps.setString(1, argument.getName());
          ps.setLong(2, argument.getId());
        };

    jdbcTemplate.batchUpdate(
        UPDATE_SQL,
        sampleEntities,
        1000000,
        statementSetter);

    stopWatch.stop();
    logRuntime("updateNamesForEntities_Approach01", stopWatch);
  }

  public void updateNamesForEntities_Approach02(List<SampleEntity> sampleEntities) {
    StopWatch stopWatch = new StopWatch();
    stopWatch.start();
    BatchPreparedStatementSetter statementSetter = new BatchPreparedStatementSetter() {
      public void setValues(PreparedStatement ps, int i)
          throws SQLException {
        ps.setString(1, sampleEntities.get(i).getName());
        ps.setLong(2, sampleEntities.get(i).getId());
      }

      public int getBatchSize() {
        return sampleEntities.size();
      }
    };

    jdbcTemplate.batchUpdate(UPDATE_SQL, statementSetter);

    stopWatch.stop();
    logRuntime("updateNamesForEntities_Approach02", stopWatch);
  }

  public void updateNamesForEntities_Approach03(List<SampleEntity> sampleEntities) {
    StopWatch stopWatch = new StopWatch();
    stopWatch.start();

    // With this approach, we cannot update all entities at the same time.
    // We have to split it into sub-lists, and update each sup-list one by one.
    final AtomicInteger subListIndex = new AtomicInteger();
    String[] queries = sampleEntities.stream()
        .collect(Collectors.groupingBy(t -> subListIndex.getAndIncrement() / BATCH_SIZE))
        .values()
        .stream()
        .map(entities -> createQueryForUpdateNames(entities))
        .toArray(String[]::new);

    jdbcTemplate.batchUpdate(queries);

    stopWatch.stop();
    logRuntime("updateNamesForEntities_Approach03", stopWatch);
  }

  /**
   * @param sampleEntities the list of sample entities
   * @return
   */
  private String createQueryForUpdateNames(List<SampleEntity> sampleEntities) {
    String ids =
        sampleEntities.stream().map(entity -> String.valueOf(entity.getId()))
            .collect(Collectors.joining(", "));
    StringBuilder query = new StringBuilder("UPDATE sample_entity SET name = CASE ");
    for (SampleEntity entity : sampleEntities) {
      query.append(String.format("WHEN id=%d THEN '%s' ", entity.getId(), entity.getName()));
    }
    query.append(String.format("END WHERE id IN (%s) ", ids));
    return query.toString();
  }

  private void logRuntime(String messageDescription, StopWatch stopWatch) {
    log.info(messageDescription + " Runtime: {} millis ({} secs)",
        stopWatch.getTotalTimeMillis(),
        (double) stopWatch.getTotalTimeMillis() / 1000d);
  }
}
