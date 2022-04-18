package org.tnmk.practicespringjpa.pro10transaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@EnableAsync
public class Pro10TransactionApplication {

  public static void main(String[] args) {
    SpringApplication.run(Pro10TransactionApplication.class, args);
  }

  @PostConstruct
  public void init() {
    // Always run app in UTC timezone to solve the timezone problem
    // when loading Timestamp column (in UTC) from MySQL to a ZonedDateTime/OffsetDateTime
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
  }
}
