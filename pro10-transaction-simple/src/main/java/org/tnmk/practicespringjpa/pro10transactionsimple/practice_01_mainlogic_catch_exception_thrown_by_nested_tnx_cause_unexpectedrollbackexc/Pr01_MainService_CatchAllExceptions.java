package org.tnmk.practicespringjpa.pro10transactionsimple.practice_01_mainlogic_catch_exception_thrown_by_nested_tnx_cause_unexpectedrollbackexc;

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
public class Pr01_MainService_CatchAllExceptions {

  private final Pr01_NestedService_ThrowException nestedService;
  private final SimpleRepository simpleRepository;

  @Transactional
  public SaveEntitiesResult saveEntities(
      SimpleEntity toBeSavedInMainMethod,
      SimpleEntity toBeSavedInPrivateMethod,
      SimpleEntity toBeSavedInNestedService_withNestedTnx,
      SimpleEntity toBeSavedInNestedService_withoutNestedTnx) throws IllegalArgumentException {

    SimpleEntity alwaysSuccessEntity = simpleRepository.save(new SimpleEntity("AlwaysSuccess" + UUID.randomUUID()));

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

    // toBeSavedInNestedService_withNestedTnx
    try {
      toBeSavedInNestedService_withNestedTnx = nestedService.saveWithNestedTransaction(toBeSavedInNestedService_withNestedTnx);
    } catch (Exception ex) {
      // This case actually will cause UnexpectedRollbackException:
      // https://stackoverflow.com/questions/2007097/unexpectedrollbackexception-a-full-scenario-analysis
      toBeSavedInNestedService_withNestedTnx = null;
    }

    // toBeSavedInNestedService_withoutNestedTnx
    try {
      toBeSavedInNestedService_withoutNestedTnx = nestedService.saveWithoutNestedTransaction(toBeSavedInNestedService_withoutNestedTnx);
    } catch (Exception ex) {
      // This won't cause UnexpectedRollbackException:
      toBeSavedInNestedService_withoutNestedTnx = null;
    }

    return new SaveEntitiesResult(alwaysSuccessEntity, toBeSavedInMainMethod, toBeSavedInPrivateMethod, toBeSavedInNestedService_withNestedTnx,
        toBeSavedInNestedService_withoutNestedTnx);
  }

  private SimpleEntity saveInPrivateMethod(SimpleEntity saveInPrivateMethod) throws IllegalArgumentException {
    if (saveInPrivateMethod.getName() == null) {
      throw new IllegalArgumentException("name cannot be null");
    }
    return simpleRepository.save(saveInPrivateMethod);
  }

}
