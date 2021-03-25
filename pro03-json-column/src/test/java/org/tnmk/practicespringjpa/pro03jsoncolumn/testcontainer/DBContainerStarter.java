package org.tnmk.practicespringjpa.pro03jsoncolumn.testcontainer;

import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.time.Duration;

public class DBContainerStarter {
    public static final MySQLContainer DB_CONTAINER = startContainer();

    private static MySQLContainer startContainer() {
        MySQLContainer mySQLContainer = (MySQLContainer) new MySQLContainer()
            .withDatabaseName("micro_mysql_account_management_db")
            .withUsername("default")
//            .withPassword("test")//Just an embedded account's password for automation testing.
            .waitingFor(
                Wait.forLogMessage("ready for connection",1).withStartupTimeout(Duration.ofSeconds(60))
            );
        mySQLContainer.start();
        return mySQLContainer;
    }

}
