package org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr03_eager_loading;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr03_eager_loading.testdata.ParentAndChildren;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr03_eager_loading.testdata.ParentAndChildrenFixtures;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.testinfra.BaseSpringTest_WithActualDb;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class Pr03_EagerLoadingTest extends BaseSpringTest_WithActualDb {

  @Autowired
  private ParentAndChildrenFixtures fixtures;
  @Autowired
  private ChildService childService;

  @Test
  public void when_FindChildById_then_TheEagerLoadedParent_WillAlsoBeReturned() {
    // GIVEN:
    ParentAndChildren parentAndChildren = fixtures.createParentAndChild("parent", 1);
    ChildEntity child = parentAndChildren.getChildren().get(0);

    // WHEN:
    log.info("When find child by id {}...", child.getId());
    // When looking at the log messages, because ParentEntity is eager loaded along with ChildEntity,
    // we should see only one `SELECT ... FROM child_entity LEFT OUTER JOIN parent ...`
    ChildEntity childEntityInDB = childService.findById(child.getId()).get();

    // THEN:
    log.info("Assertions...");
    Assertions.assertEquals(parentAndChildren.getParent().getName(), childEntityInDB.getParentEntity().getName());
  }

  @Test
  public void when_FindChildrenByIds_then_TheEagerLoadedParents_WillAlsoBeReturned() {
    // GIVEN:
    List<ParentAndChildren> parentAndChildren = fixtures.createParentsAndChildren(3, 2);

    List<Long> childrenIds = Arrays.asList(
        parentAndChildren.get(0).getChildren().get(0).getId(),
        parentAndChildren.get(0).getChildren().get(1).getId(),
        parentAndChildren.get(1).getChildren().get(0).getId(),
        parentAndChildren.get(2).getChildren().get(0).getId()
    );
    // WHEN:
    //  As we can guess, the number of SQL statements are executed is different from
    //  the previous test case `when_FindChildById_then_TheEagerLoadedParent_WillAlsoBeReturned()`
    log.info("When find children by ids {}...", childrenIds);
    List<ChildEntity> childrenInDB = childService.findByIds(childrenIds);

    // THEN:
    log.info("Assertions...");
    Assertions.assertEquals(childrenIds.size(), childrenInDB.size());
    for (ChildEntity childEntityInDB : childrenInDB) {
      Assertions.assertNotNull(childEntityInDB.getParentEntity().getName());
    }
  }

  @Test
  public void when_FindChildrenByName_then_TheEagerLoadedParents_WillAlsoBeReturned() {
    // GIVEN:
    List<ParentAndChildren> parentAndChildren = fixtures.createParentsAndChildren(3, 2);
    String typicalChildrenName = parentAndChildren.get(0).getChildren().get(0).getName().substring(0, 5);

    // WHEN:
    //  This is the only difference from the above test case
    //  `whenFindChildrenByIds_TheEagerLoadedParents_WillAlsoBeReturned().
    //  However, the interesting thing is: the number of SQL executions are different!!!
    //  Please take a look at the log message.
    log.info("When find children by name '{}'...", typicalChildrenName);
    List<ChildEntity> childrenInDB = childService.findByNameContaining(typicalChildrenName);

    // THEN:
    log.info("Assertions...");
    Assertions.assertTrue(!childrenInDB.isEmpty());
    for (ChildEntity childEntityInDB : childrenInDB) {
      Assertions.assertNotNull(childEntityInDB.getParentEntity().getName());
    }
  }
}
