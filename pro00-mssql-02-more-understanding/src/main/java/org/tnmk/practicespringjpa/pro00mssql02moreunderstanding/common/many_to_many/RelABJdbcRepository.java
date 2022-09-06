package org.tnmk.practicespringjpa.pro00mssql02moreunderstanding.common.many_to_many;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class RelABJdbcRepository {
  private final JdbcTemplate jdbcTemplate;

  public void insertInBatch(List<RelAB> rels) {
    int[] updatedRows = this.jdbcTemplate.batchUpdate(
        "insert into ab_rel (a_id, b_id) values(?,?)",
        new BatchPreparedStatementSetter() {

          public void setValues(PreparedStatement ps, int i) throws SQLException {
            ps.setInt(1, rels.get(i).getAId());
            ps.setInt(2, rels.get(i).getBId());
          }

          public int getBatchSize() {
            return rels.size();
          }

        });
    log.info("insertInBatch: executed result: {}", Arrays.toString(updatedRows));
  }
}
