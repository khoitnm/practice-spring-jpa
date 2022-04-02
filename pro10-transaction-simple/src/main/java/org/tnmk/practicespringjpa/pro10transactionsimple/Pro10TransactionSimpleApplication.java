package org.tnmk.practicespringjpa.pro10transactionsimple;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class Pro10TransactionSimpleApplication {

  public static void main(String[] args) {
    SpringApplication.run(Pro10TransactionSimpleApplication.class, args);
  }
}
