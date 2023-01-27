package org.tnmk.practicespringjpa.pro00mssql03manydata.sample;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.stereotype.Repository;
import org.springframework.util.StopWatch;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class SampleBatchUpdateRepository {
  private final JdbcTemplate jdbcTemplate;

  public void updateEntityCodesForEntities(List<SampleEntity> sampleEntities) {
    StopWatch stopWatch = new StopWatch();
    stopWatch.start();

    ParameterizedPreparedStatementSetter<SampleEntity> statementSetter =
        (ps, argument) -> {
          ps.setString(1, argument.getName());
          ps.setLong(2, argument.getId());
        };

    jdbcTemplate.batchUpdate(
        "update sample_entity set name = ? where id = ?",
        sampleEntities,
        1000000,
        statementSetter);

    stopWatch.stop();
    log.info("BatchUpdateRepository Runtime: {} millis ({} secs)",
        stopWatch.getTotalTimeMillis(),
        (double) stopWatch.getTotalTimeMillis() / 1000d);
  }
}
