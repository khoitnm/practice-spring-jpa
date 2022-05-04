package org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr03_eager_vs_lazy_load.pr03_00_eager;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr03_eager_vs_lazy_load.pr03_00_eager.testdata.ParentAndChildrenWithEagerLoad;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr03_eager_vs_lazy_load.pr03_00_eager.testdata.ParentAndChildrenWithEagerLoadFixtures;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.testinfra.BaseSpringTest_WithActualDb;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class Pr03_00_EagerLoadingTest extends BaseSpringTest_WithActualDb {

  @Autowired
  private ParentAndChildrenWithEagerLoadFixtures fixtures;
  @Autowired
  private ChildWithEagerLoadService childService;

  @AfterEach
  public void cleanUp() {
    log.info("Clean up data ...");
    fixtures.cleanUpAllParentsAndChildren();
  }

  @Test
  public void when_FindChildById_then_TheEagerLoadedParent_WillAlsoBeReturned() {
    // GIVEN:
    ParentAndChildrenWithEagerLoad parentAndChildren = fixtures.createParentAndChild("parent", 1);
    ChildWithEagerLoadEntity child = parentAndChildren.getChildren().get(0);

    // WHEN:
    log.info("When find child by id {}...", child.getId());
    // When looking at the log messages, because ParentEntity is pr03_00_eager loaded along with ChildEntity,
    // we should see only one `SELECT ... FROM child_entity LEFT OUTER JOIN parent ...`
    ChildWithEagerLoadEntity childEntityInDB = childService.findById(child.getId()).get();

    // THEN:
    log.info("Assertions parent's name...");
    Assertions.assertEquals(parentAndChildren.getParent().getName(), childEntityInDB.getParentEntity().getName());
  }

  @Test
  public void when_FindChildrenByIds_then_TheEagerLoadedParents_WillAlsoBeReturned() {
    // GIVEN:
    int parentsCount = 3;
    int childrenCountPerParent = 2;
    List<ParentAndChildrenWithEagerLoad> parentAndChildren = fixtures.createParentsAndChildren(parentsCount, childrenCountPerParent);
    List<Long> childrenIds = getAllChildrenIds(parentAndChildren);

    // WHEN:
    //  As we can guess, the number of SQL statements are executed is different from
    //  the previous test case `when_FindChildById_then_TheEagerLoadedParent_WillAlsoBeReturned()`
    log.info("When find children by ids {}...", childrenIds);
    List<ChildWithEagerLoadEntity> childrenInDB = childService.findByIds(childrenIds);

    // THEN:
    log.info("Assertions parents' names...");
    for (ChildWithEagerLoadEntity childEntityInDB : childrenInDB) {
      Assertions.assertNotNull(childEntityInDB.getParentEntity().getName());
    }

    log.info("Assertions children size...");
    Assertions.assertEquals(parentsCount * childrenCountPerParent, childrenIds.size());
    Assertions.assertEquals(parentsCount * childrenCountPerParent, childrenInDB.size());
  }

  @Test
  public void when_FindChildrenByName_then_TheEagerLoadedParents_WillAlsoBeReturned() {
    // GIVEN:
    int parentsCount = 3;
    int childrenCountPerParent = 2;
    List<ParentAndChildrenWithEagerLoad> parentAndChildren = fixtures.createParentsAndChildren(parentsCount, childrenCountPerParent);
    String aPartOfChildrenName = ParentAndChildrenWithEagerLoadFixtures.CHILD_NAME_PREFIX;

    // WHEN:
    //  This is the only difference from the above test case
    //  `whenFindChildrenByIds_TheEagerLoadedParents_WillAlsoBeReturned().
    //  However, the interesting thing is: the number of SQL executions are different!!!
    //  Please take a look at the log message.
    log.info("When find children by name '{}'...", aPartOfChildrenName);
    List<ChildWithEagerLoadEntity> childrenInDB = childService.findByNameContaining(aPartOfChildrenName); // this basically returns all children

    // THEN:
    log.info("Assertions parents' names...");
    for (ChildWithEagerLoadEntity childEntityInDB : childrenInDB) {
      Assertions.assertNotNull(childEntityInDB.getParentEntity().getName());
    }

    log.info("Assertions children size...");
    Assertions.assertEquals(parentsCount * childrenCountPerParent, childrenInDB.size());
  }

  @Test
  public void when_FindChildrenByName_withNativeQuery_then_TheEagerLoadedParents_WillBeReturned() {
    // GIVEN:
    int parentsCount = 3;
    int childrenCountPerParent = 2;
    List<ParentAndChildrenWithEagerLoad> parentAndChildren = fixtures.createParentsAndChildren(parentsCount, childrenCountPerParent);
    String aPartOfChildrenName = ParentAndChildrenWithEagerLoadFixtures.CHILD_NAME_PREFIX;

    // WHEN:
    //  This is the only difference from the above test case
    //  `whenFindChildrenByIds_TheEagerLoadedParents_WillAlsoBeReturned().
    //  However, the interesting thing is: the number of SQL executions are different!!!
    //  Please take a look at the log message.
    log.info("When find children by name '{}'...", aPartOfChildrenName);
    List<ChildWithEagerLoadEntity> childrenInDB = childService.findByNameContaining_withNativeQuery(aPartOfChildrenName); // this basically returns all children

    // THEN:
    log.info("Assertions parents' names...");
    for (ChildWithEagerLoadEntity childEntityInDB : childrenInDB) {
      Assertions.assertNotNull(childEntityInDB.getParentEntity().getName());
    }

    log.info("Assertions children size...");
    Assertions.assertEquals(parentsCount * childrenCountPerParent, childrenInDB.size());
  }

  private static List<Long> getAllChildrenIds(List<ParentAndChildrenWithEagerLoad> parentsAndChildren) {
    List<Long> childrenIds = parentsAndChildren.stream().flatMap(
        parentAndChildrenItem -> toChildrenIds(parentAndChildrenItem.getChildren()).stream()
    ).collect(Collectors.toList());
    return childrenIds;
  }

  private static List<Long> toChildrenIds(List<ChildWithEagerLoadEntity> children) {
    return children.stream().map(ChildWithEagerLoadEntity::getId).collect(Collectors.toList());
  }
}
