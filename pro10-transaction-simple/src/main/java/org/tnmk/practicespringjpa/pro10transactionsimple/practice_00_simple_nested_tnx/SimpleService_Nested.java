package org.tnmk.practicespringjpa.pro10transactionsimple.practice_00_simple_nested_tnx;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class SimpleService_Nested {
  private final SimpleRepository simpleRepository;

  @Transactional
  public SimpleEntity save(SimpleEntity toBeSavedInMainMethod) {
    if (toBeSavedInMainMethod.getName() == null) {
      throw new IllegalStateException("name cannot be null");
    }
    return simpleRepository.save(toBeSavedInMainMethod);
  }
}
