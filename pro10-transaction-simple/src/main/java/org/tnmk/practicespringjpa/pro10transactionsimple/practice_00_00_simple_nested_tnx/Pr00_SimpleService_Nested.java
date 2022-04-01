package org.tnmk.practicespringjpa.pro10transactionsimple.practice_00_00_simple_nested_tnx;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SimpleEntity;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SimpleRepository;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class Pr00_SimpleService_Nested {
  private final SimpleRepository simpleRepository;

  @Transactional
  public SimpleEntity save(SimpleEntity toBeSavedInMainMethod) throws IllegalArgumentException{
    if (toBeSavedInMainMethod.getName() == null) {
      throw new IllegalArgumentException("name cannot be null");
    }
    return simpleRepository.save(toBeSavedInMainMethod);
  }
}
