package org.tnmk.practicespringjpa.pro10transactionsimple.practice_00_simple_nested_tnx;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SaveEntitiesResult;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SimpleEntity;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SimpleRepository;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class Pr00_SimpleService_Main {

  private final Pr00_SimpleService_Nested pr00SimpleService_nested;
  private final SimpleRepository simpleRepository;

  @Transactional
  public SaveEntitiesResult saveEntities(
      SimpleEntity toBeSavedInMainMethod,
      SimpleEntity toBeSavedInPrivateMethod,
      SimpleEntity toBeSavedInNestedService) throws IllegalArgumentException {

    SimpleEntity alwaysSuccessEntity = simpleRepository.save(new SimpleEntity("AlwaysSuccess" + UUID.randomUUID()));

    if (toBeSavedInMainMethod.getName() == null) {
      throw new IllegalArgumentException("name cannot be null");
    }
    toBeSavedInMainMethod = simpleRepository.save(toBeSavedInMainMethod);

    toBeSavedInPrivateMethod = saveInPrivateMethod(toBeSavedInPrivateMethod);

    toBeSavedInNestedService = pr00SimpleService_nested.save(toBeSavedInNestedService);

    return new SaveEntitiesResult(alwaysSuccessEntity, toBeSavedInMainMethod, toBeSavedInPrivateMethod, toBeSavedInNestedService, null);
  }

  private SimpleEntity saveInPrivateMethod(SimpleEntity saveInPrivateMethod) throws IllegalArgumentException {
    if (saveInPrivateMethod.getName() == null) {
      throw new IllegalArgumentException("name cannot be null");
    }
    return simpleRepository.save(saveInPrivateMethod);
  }

}
