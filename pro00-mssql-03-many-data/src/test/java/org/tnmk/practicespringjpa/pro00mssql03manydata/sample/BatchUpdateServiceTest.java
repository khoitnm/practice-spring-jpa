package org.tnmk.practicespringjpa.pro00mssql03manydata.sample;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.practicespringjpa.pro00mssql03manydata.testinfra.BaseSpringTest_WithActual_MsSqlServer;

import java.util.List;

@Slf4j
public class BatchUpdateServiceTest extends BaseSpringTest_WithActual_MsSqlServer {
  @Autowired
  private BatchUpdateService batchUpdateService;

  /**
   * Before running this test case, please run the script in
   * `practice-spring-jpa\pro00-mssql-03-many-data\support-scripts\01_random_tbl_sample_entity.sql`
   * to insert sample data into this table.
   */
  @Test
  public void updateTopItems_Successfully() {
    batchUpdateService.updateTopItems(2);
  }
}
