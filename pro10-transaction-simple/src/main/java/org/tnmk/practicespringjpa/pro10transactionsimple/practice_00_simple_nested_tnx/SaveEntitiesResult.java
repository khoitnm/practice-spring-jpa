package org.tnmk.practicespringjpa.pro10transactionsimple.practice_00_simple_nested_tnx;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SaveEntitiesResult {
  private final SimpleEntity toBeSavedInMainMethod;
  private final SimpleEntity toBeSavedInPrivateMethod;
  private final SimpleEntity toBeSavedInNestedService;
}
