package org.tnmk.practicespringjpa.pro07multitenant.testinfra.dbtestcontainer;

import org.testcontainers.containers.MSSQLServerContainer;

import java.time.Duration;

public class DBContainerStarter {
  public static final MSSQLServerContainer DB_CONTAINER = startContainer();

  private static MSSQLServerContainer startContainer() {
    MSSQLServerContainer container = new MSSQLServerContainer();
    container
        .withInitScript("db/init.sql")
        .withStartupTimeout(Duration.ofSeconds(90))
        .start();
    container.start();
    return container;
  }

}
