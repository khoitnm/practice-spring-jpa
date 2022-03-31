package org.tnmk.practicespringjpa.pro10transactionsimple.practice_02_partial_tnx_in_nested_service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SaveEntitiesResult;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SimpleEntity;
import org.tnmk.practicespringjpa.pro10transactionsimple.common.SimpleRepository;

import javax.transaction.Transactional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class Pr02_MainService_CatchAllExceptions {

  private final Pr02_NestedService_WithPartialTnx nestedService;
  private final SimpleRepository simpleRepository;

  @Transactional
  public SaveEntitiesResult saveEntities(
      SimpleEntity toBeSavedInMainMethod,
      SimpleEntity toBeSavedInPrivateMethod,
      SimpleEntity toBeSavedInNestedService,
      boolean isExpectUpdateAfterSavingInNestedServiceSuccess
  ) throws IllegalArgumentException {

    SimpleEntity alwaysSuccessEntity = simpleRepository.save(new SimpleEntity("AlwaysSuccessInMainService" + UUID.randomUUID()));

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
      toBeSavedInNestedService = nestedService.save(toBeSavedInNestedService, isExpectUpdateAfterSavingInNestedServiceSuccess);
    } catch (Exception ex) {
      // This case actually will cause UnexpectedRollbackException:
      // https://stackoverflow.com/questions/2007097/unexpectedrollbackexception-a-full-scenario-analysis
      toBeSavedInNestedService = null;
    }

    return new SaveEntitiesResult(alwaysSuccessEntity, toBeSavedInMainMethod, toBeSavedInPrivateMethod, toBeSavedInNestedService);
  }

  private SimpleEntity saveInPrivateMethod(SimpleEntity saveInPrivateMethod) throws IllegalArgumentException {
    if (saveInPrivateMethod.getName() == null) {
      throw new IllegalArgumentException("name cannot be null");
    }
    return simpleRepository.save(saveInPrivateMethod);
  }

}
