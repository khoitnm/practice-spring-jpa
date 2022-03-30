package org.tnmk.practicespringjpa.pro01simplemysql.testinfra.dbtestcontainer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import java.lang.invoke.MethodHandles;

import static org.tnmk.practicespringjpa.pro01simplemysql.testinfra.dbtestcontainer.DBContainerStarter.DB_CONTAINER;
import static org.tnmk.practicespringjpa.pro01simplemysql.testinfra.dbtestcontainer.DBContainerStarter.ROOT_PASSWORD;

public class DBContainerContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

  private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  public void initialize(final ConfigurableApplicationContext configurableApplicationContext) {
    logger.info("DBContainerTest"
        + "\nJdbcUrl:\t" + DB_CONTAINER.getJdbcUrl()
        + "\nusername:\t" + DB_CONTAINER.getUsername()
        + "\npassword:\t" + DB_CONTAINER.getPassword());
    TestPropertyValues.of(
        "spring.datasource.url=" + DB_CONTAINER.getJdbcUrl(),
        "spring.datasource.username=" + DB_CONTAINER.getUsername(),
        "spring.datasource.password=" + DB_CONTAINER.getPassword(),

        // The root username is hardcoded inside {@link MySQLContainer#MYSQL_ROOT_USER}
        "spring.sql.init.username=root",
        "spring.sql.init.password=" + ROOT_PASSWORD
    ).applyTo(configurableApplicationContext.getEnvironment());
  }
}