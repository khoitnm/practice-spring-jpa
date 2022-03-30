package org.tnmk.practicespringjpa.pro10transactionsimple.practice_00_simple_nested_tnx;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class SimpleService_Main {

  private final SimpleService_Nested simpleService_nested;
  private final SimpleRepository simpleRepository;

  @Transactional
  public SaveEntitiesResult saveEntities(
      SimpleEntity toBeSavedInMainMethod,
      SimpleEntity toBeSavedInPrivateMethod,
      SimpleEntity toBeSavedInNestedService) {

    if (toBeSavedInMainMethod.getName() == null) {
      throw new IllegalStateException("name cannot be null");
    }
    toBeSavedInMainMethod = simpleRepository.save(toBeSavedInMainMethod);

    toBeSavedInPrivateMethod = saveInPrivateMethod(toBeSavedInPrivateMethod);

    toBeSavedInNestedService = simpleService_nested.save(toBeSavedInNestedService);

    return new SaveEntitiesResult(toBeSavedInMainMethod, toBeSavedInPrivateMethod, toBeSavedInNestedService);
  }

  private SimpleEntity saveInPrivateMethod(SimpleEntity saveInPrivateMethod) {
    if (saveInPrivateMethod.getName() == null) {
      throw new IllegalStateException("name cannot be null");
    }
    return simpleRepository.save(saveInPrivateMethod);
  }

}
