package org.tnmk.practicespringjpa.pro10transactionsimple.common.utils;

public class ThreadUtils {
  public static void sleep(int milliseconds) {
    try {
      Thread.sleep(milliseconds);
    } catch (InterruptedException e) {
      throw new IllegalStateException(e);
    }
  }
}
