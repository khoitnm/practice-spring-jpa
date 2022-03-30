package org.tnmk.practicespringjpa.pro10transactionsimple.practice_01_mainlogic_catch_exception_in_nested_tnx;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SimpleEntity;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SimpleRepository;
import org.tnmk.practicespringjpa.pro10transactionsimple.practice_00_simple_nested_tnx.SaveEntitiesResult;

import javax.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MainService_CatchAllExceptions {

  private final NestedService_ThrowException nestedService;
  private final SimpleRepository simpleRepository;

  @Transactional
  public SaveEntitiesResult saveEntities(
      SimpleEntity toBeSavedInMainMethod,
      SimpleEntity toBeSavedInPrivateMethod,
      SimpleEntity toBeSavedInNestedService) throws IllegalArgumentException {

    // toBeSavedInMainMethod
    try {
      if (toBeSavedInMainMethod.getName() == null) {
        throw new IllegalArgumentException("name cannot be null");
      }
      toBeSavedInMainMethod = simpleRepository.save(toBeSavedInMainMethod);
    } catch (Exception ex) {
      toBeSavedInMainMethod = null;
    }

    // toBeSavedInPrivateMethod
    try {
      toBeSavedInPrivateMethod = saveInPrivateMethod(toBeSavedInPrivateMethod);
    } catch (Exception ex) {
      toBeSavedInPrivateMethod = null;
    }

    // toBeSavedInNestedService
    try {
      toBeSavedInNestedService = nestedService.save(toBeSavedInNestedService);
    } catch (Exception ex) {
      toBeSavedInNestedService = null;
    }

    return new SaveEntitiesResult(toBeSavedInMainMethod, toBeSavedInPrivateMethod, toBeSavedInNestedService);
  }

  private SimpleEntity saveInPrivateMethod(SimpleEntity saveInPrivateMethod) throws IllegalArgumentException {
    if (saveInPrivateMethod.getName() == null) {
      throw new IllegalArgumentException("name cannot be null");
    }
    return simpleRepository.save(saveInPrivateMethod);
  }

}
