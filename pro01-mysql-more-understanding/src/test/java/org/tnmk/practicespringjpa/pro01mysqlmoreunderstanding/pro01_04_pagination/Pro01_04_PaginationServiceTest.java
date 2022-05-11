package org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pro01_04_pagination;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.common.SimpleEntity;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.common.SimpleRepository;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.common.testdata.SimpleEntityFixture;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.testinfra.BaseSpringTest_WithActualDb;

import java.util.List;

@Slf4j
public class Pro01_04_PaginationServiceTest extends BaseSpringTest_WithActualDb {
  @Autowired
  private SimpleEntityFixture simpleEntityFixture;
  @Autowired
  private Pro01_04_SimpleRepository simpleRepository;

  @Test
  public void when_doingPagination_then_2QueriesAreExecuted_OneForFindElementsInAPage_AndAnotherForCountingTotalElements() {
    // Given
    log.info("Test: Prepare data");
    int entitiesCount = 5;
    simpleEntityFixture.random(entitiesCount);

    // When
    log.info("Test: when executing the main logic");
    PageRequest pageRequest = PageRequest.of(1, 2);
    List<SimpleEntity> entities = simpleRepository.findByNameContaining(SimpleEntityFixture.ENTITY_NAME_PREFIX, pageRequest).getContent();

    // Then
    log.info("Test: then assert the result");
    Assertions.assertEquals(pageRequest.getPageSize(), entities.size());
  }

  @Test
  public void when_doingPagination_JPQL_then_2QueriesAreExecuted_OneForFindElementsInAPage_AndAnotherForCountingTotalElements() {
    // Given
    log.info("Test: Prepare data");
    int entitiesCount = 5;
    simpleEntityFixture.random(entitiesCount);

    // When
    log.info("Test: when executing the main logic");
    PageRequest pageRequest = PageRequest.of(1, 2);
    List<SimpleEntity> entities = simpleRepository.findByNameContaining_JPQL(SimpleEntityFixture.ENTITY_NAME_PREFIX, pageRequest).getContent();

    // Then
    log.info("Test: then assert the result");
    Assertions.assertEquals(pageRequest.getPageSize(), entities.size());
  }

  @Test
  public void when_doingPagination_NativeQuery_then_2QueriesAreExecuted_OneForFindElementsInAPage_AndAnotherForCountingTotalElements() {
    // Given
    log.info("Test: Prepare data");
    int entitiesCount = 5;
    simpleEntityFixture.random(entitiesCount);

    // When
    log.info("Test: when executing the main logic");
    PageRequest pageRequest = PageRequest.of(1, 2);
    List<SimpleEntity> entities = simpleRepository.findByNameContaining_NativeQuery(SimpleEntityFixture.ENTITY_NAME_PREFIX, pageRequest).getContent();

    // Then
    log.info("Test: then assert the result");
    Assertions.assertEquals(pageRequest.getPageSize(), entities.size());
  }

  @Disabled // This approach still doesn't work because cannot define the count query in NamedNativeQuery
  @Test
  public void when_doingPagination_NamedNativeQuery_then_2QueriesAreExecuted_OneForFindElementsInAPage_AndAnotherForCountingTotalElements() {
    // Given
    log.info("Test: Prepare data");
    int entitiesCount = 5;
    simpleEntityFixture.random(entitiesCount);

    // When
    log.info("Test: when executing the main logic");
    PageRequest pageRequest = PageRequest.of(1, 2);
    List<SimpleEntity> entities = simpleRepository.findByNameContaining_NamedNativeQuery(SimpleEntityFixture.ENTITY_NAME_PREFIX, pageRequest).getContent();

    // Then
    log.info("Test: then assert the result");
    Assertions.assertEquals(pageRequest.getPageSize(), entities.size());
  }
}
