package org.tnmk.practicespringjpa.pro04flyway;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.stereotype.Component;

/**
 * Only enable this when there a fail flyway script was executed.
 */
//@Component
public class FlywayStrategy implements FlywayMigrationStrategy {

  private static final Logger logger = LoggerFactory.getLogger(FlywayStrategy.class);

  @Override
  public void migrate(Flyway flyway) {
    logger.info("running flyway");
    flyway.repair();//This code is use to repair all wrong executed flyway script by remove the corresponding record in DB.
    flyway.migrate();
    logger.info("done running flyway");
  }
}