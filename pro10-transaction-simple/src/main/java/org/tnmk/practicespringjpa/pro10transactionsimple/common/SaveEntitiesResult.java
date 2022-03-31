package org.tnmk.practicespringjpa.pro10transactionsimple.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SimpleEntity;

@Getter
@RequiredArgsConstructor
public class SaveEntitiesResult {
  private final SimpleEntity alwaysSuccessInMainMethod;
  private final SimpleEntity toBeSavedInMainMethod;
  private final SimpleEntity toBeSavedInPrivateMethod;
  private final SimpleEntity toBeSavedInNestedService;
}
