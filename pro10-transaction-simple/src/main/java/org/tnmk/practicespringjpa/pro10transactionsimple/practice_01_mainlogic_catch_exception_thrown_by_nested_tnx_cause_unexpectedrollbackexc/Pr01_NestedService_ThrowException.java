package org.tnmk.practicespringjpa.pro10transactionsimple.practice_01_mainlogic_catch_exception_thrown_by_nested_tnx_cause_unexpectedrollbackexc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SimpleEntity;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SimpleRepository;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class Pr01_NestedService_ThrowException {
  private final SimpleRepository simpleRepository;

  @Transactional
  public SimpleEntity saveWithNestedTransaction(SimpleEntity toBeSavedInMainMethod) throws IllegalArgumentException{
    return save(toBeSavedInMainMethod);
  }

  public SimpleEntity saveWithoutNestedTransaction(SimpleEntity toBeSavedInMainMethod) throws IllegalArgumentException{
    return save(toBeSavedInMainMethod);
  }

  public SimpleEntity save(SimpleEntity toBeSavedInMainMethod) throws IllegalArgumentException{
    if (toBeSavedInMainMethod.getName() == null) {
      throw new IllegalArgumentException("name cannot be null");
    }
    return simpleRepository.save(toBeSavedInMainMethod);
  }
}
