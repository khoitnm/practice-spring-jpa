package org.tnmk.practicespringjpa.pro09hirakiconnectionpool.repository;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ConnectionLeakRepository {

  private final DataSource dataSource;

  @SneakyThrows
  public void createConnectionWithoutClosing() {
    Connection connection = dataSource.getConnection();
    log.info("New Connection which will not be closed: {}", connection);
  }
}
