package org.tnmk.practicespringjpa.pro10transaction.practice_02_01_partial_tnx_in_nested_service__independant_tnx_by__tnx_requires_new;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringjpa.pro10transaction.common.SimpleEntity;
import org.tnmk.practicespringjpa.pro10transaction.common.SimpleRepository;

import javax.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class Pr02_01_MainService_CatchAllExceptions {

  private final Pr02_01_NestedService_WithPartialTnx nestedService;
  private final SimpleRepository simpleRepository;

  @Transactional
  public MainResult saveEntities(
      String alwaysSuccessName_InMainService,
      String alwaysSuccessName_InNestedService_PartialTnx,
      String entity_InNestedService_PartialTnx,
      String entity_InNestedService_AfterPartialTnx
  ) throws IllegalArgumentException {
    // Note: when working with transaction, catching exception in main service is different from catching in nested service.
    // alwaysSuccessEntity
    SimpleEntity alwaysSuccessEntity = simpleRepository.save(new SimpleEntity(alwaysSuccessName_InMainService));

    // toBeSavedInNestedService
    Pr02_01_NestedService_WithPartialTnx.NestedResult nestedResult;
    try {
      nestedResult = nestedService.saveWithPartialTnx(
          alwaysSuccessName_InNestedService_PartialTnx,
          entity_InNestedService_PartialTnx,
          entity_InNestedService_AfterPartialTnx);
    } catch (Exception ex) {
      nestedResult = null;
    }

    return new MainResult(alwaysSuccessEntity, nestedResult);
  }

  @RequiredArgsConstructor
  @Getter
  public static class MainResult {
    private final SimpleEntity alwaysSuccessInMainService;
    private final Pr02_01_NestedService_WithPartialTnx.NestedResult nestedResult;
  }
}
