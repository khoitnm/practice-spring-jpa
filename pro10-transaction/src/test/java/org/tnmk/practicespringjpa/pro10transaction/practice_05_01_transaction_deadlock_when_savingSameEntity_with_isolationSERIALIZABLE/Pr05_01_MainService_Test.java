package org.tnmk.practicespringjpa.pro10transaction.practice_05_01_transaction_deadlock_when_savingSameEntity_with_isolationSERIALIZABLE;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.CannotAcquireLockException;
import org.tnmk.practicespringjpa.pro10transaction.testinfra.BaseSpringTest_WithActualDb;

import java.util.UUID;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;

@Slf4j
public class Pr05_01_MainService_Test extends BaseSpringTest_WithActualDb {
  @Autowired
  private Pr05_01_MainService service;

  @Test
  public void test_startSlowBeforeFast() throws ExecutionException, InterruptedException {
    String slowName = "Slow_" + UUID.randomUUID();
    String fastName = "Fast_" + UUID.randomUUID();
    int slowRuntime = 5000;
    int delayFastService = 500;

    try {
      // Dead lock happens, so cannot acquire lock:
      // It happens because of Isolation.SERIALIZABLE in Pr05_01_FastService_Async & Pr05_01_SlowService_Async
      service.slowFirst_fastLater(slowName, fastName, slowRuntime, delayFastService);

      Assertions.fail("The above method should throw an exception because of dead lock.");
    } catch (CompletionException ex) {
      Throwable cause = ex.getCause();
      Assertions.assertTrue(cause instanceof CannotAcquireLockException);
    }
  }
}
