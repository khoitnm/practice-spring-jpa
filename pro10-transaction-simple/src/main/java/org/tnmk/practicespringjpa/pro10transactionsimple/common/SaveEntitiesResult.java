package org.tnmk.practicespringjpa.pro10transactionsimple.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SaveEntitiesResult {
  private final SimpleEntity alwaysSuccessInMainMethod;
  private final SimpleEntity toBeSavedInMainMethod;
  private final SimpleEntity toBeSavedInPrivateMethod;
  private final SimpleEntity toBeSavedInNestedService_withNestedTnx;
  private final SimpleEntity toBeSavedInNestedService_withoutNestedTnx;
}
