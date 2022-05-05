package org.tnmk.practicespringjpa.pro10transaction.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase;
import org.springframework.boot.ansi.AnsiColor;

public class BrightGrayConverter extends ForegroundCompositeConverterBase<ILoggingEvent> {

  @Override
  protected String getForegroundColorCode(ILoggingEvent event) {
    // AnsiColor.WHITE is actually gray.
    // If you want real white, use AnsiColor.BRIGHT_WHITE
    return AnsiColor.WHITE.toString();

  }

}