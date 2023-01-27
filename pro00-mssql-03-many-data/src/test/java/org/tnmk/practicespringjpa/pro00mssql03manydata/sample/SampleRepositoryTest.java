package org.tnmk.practicespringjpa.pro00mssql03manydata.sample;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.practicespringjpa.pro00mssql03manydata.testinfra.BaseSpringTest_WithActual_MsSqlServer;

import java.util.List;

@Slf4j
public class SampleRepositoryTest extends BaseSpringTest_WithActual_MsSqlServer {
  @Autowired
  private SampleRepository sampleRepository;

  /**
   * Before running this test case, please run the script in
   * `practice-spring-jpa\pro00-mssql-03-many-data\support-scripts\01_random_tbl_sample_entity.sql`
   * to insert sample data into this table.
   */
  @Test
  public void findAll_1M_items_Successfully() {
    List<SampleEntity> entities = sampleRepository.findAllItems();
    log.info("Entities count: {}", entities.size());
  }
}
