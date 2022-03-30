package org.tnmk.practicespringjpa.pro10transactionsimple.testinfra.dbtestcontainer;

import org.testcontainers.containers.MySQLContainer;

import java.time.Duration;

public class DBContainerStarter {
  public static final MySQLContainer DB_CONTAINER = startContainer();
  public static final String ROOT_PASSWORD = "root";

  private static MySQLContainer startContainer() {
    MySQLContainer container = new MySQLContainer()
        .withUsername("user")
        .withPassword("user")
        .withDatabaseName("test");
    container
        .withEnv("MYSQL_ROOT_PASSWORD", ROOT_PASSWORD)
        .withStartupTimeout(Duration.ofSeconds(90))
        .start();
    return container;
  }

}
