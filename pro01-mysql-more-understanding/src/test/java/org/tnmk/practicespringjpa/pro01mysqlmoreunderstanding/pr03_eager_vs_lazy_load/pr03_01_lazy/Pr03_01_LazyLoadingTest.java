package org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr03_eager_vs_lazy_load.pr03_01_lazy;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr03_eager_vs_lazy_load.pr03_01_lazy.testdata.ParentAndChildrenWithLazyLoad;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr03_eager_vs_lazy_load.pr03_01_lazy.testdata.ParentAndChildrenWithLazyLoadFixtures;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.testinfra.BaseSpringTest_WithActualDb;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class Pr03_01_LazyLoadingTest extends BaseSpringTest_WithActualDb {

  @Autowired
  private ParentAndChildrenWithLazyLoadFixtures fixtures;
  @Autowired
  private ChildWithLazyLoadService childService;

  @Test
  public void when_FindChildById_then_TheLazyLoadParent_WontBeReturned() {
    // GIVEN:
    ParentAndChildrenWithLazyLoad parentAndChildren = fixtures.createParentAndChild("parent", 1);
    ChildWithLazyLoadEntity child = parentAndChildren.getChildren().get(0);

    // WHEN:
    log.info("When finding child by id {}...", child.getId());
    // When looking at the log messages, because ParentEntity is lazy loaded along with ChildEntity,
    // we should see only one `SELECT ... FROM child_entity LEFT OUTER JOIN parent ...`
    ChildWithLazyLoadEntity childEntityInDB = childService.findById(child.getId()).get();

    // THEN:
    log.info("Assertions...");
    Assertions.assertEquals(parentAndChildren.getParent().getId(), childEntityInDB.getParentEntity().getId());

    Assertions.assertThrows(LazyInitializationException.class, () -> {
      //  It will get LazyInitializationException because:
      //  - ParentEntity only has value in Id field with lazy loading.
      //  - So, when accessing other field in ParentEntity, Hibernate will try to get more data from DB (by executing SQL `select ...`)
      //  - But this getName() is executed outside of Service class, which means there's no more Session.
      //    Hen we get exception.
      childEntityInDB.getParentEntity().getName();
    });
  }

  @Test
  public void when_FindChildrenByIds_then_TheLazyLoadParents_WontBeReturned() {
    // GIVEN:
    List<ParentAndChildrenWithLazyLoad> parentAndChildren = fixtures.createParentsAndChildren(3, 2);

    List<Long> childrenIds = Arrays.asList(
        parentAndChildren.get(0).getChildren().get(0).getId(),
        parentAndChildren.get(0).getChildren().get(1).getId(),
        parentAndChildren.get(1).getChildren().get(0).getId(),
        parentAndChildren.get(2).getChildren().get(0).getId()
    );
    // WHEN:
    //  As we can guess, the number of SQL statements are executed is different from
    //  the previous test case `when_FindChildById_then_TheLazyLoadedParent_WillAlsoBeReturned()`
    log.info("When finding children by ids {}...", childrenIds);
    List<ChildWithLazyLoadEntity> childrenInDB = childService.findByIds(childrenIds);

    // THEN:
    log.info("Assertions...");
    Assertions.assertEquals(childrenIds.size(), childrenInDB.size());
    for (ChildWithLazyLoadEntity childEntityInDB : childrenInDB) {
      Assertions.assertNotNull(childEntityInDB.getParentEntity().getId());

      Assertions.assertThrows(LazyInitializationException.class, () -> {
        // The reason for getting LazyInitializationException was already explained in the first test case.
        childEntityInDB.getParentEntity().getName();
      });
    }
  }

  @Test
  public void when_FindChildrenByName_then_TheLazyLoadParents_WontBeReturned() {
    // GIVEN:
    List<ParentAndChildrenWithLazyLoad> parentAndChildren = fixtures.createParentsAndChildren(3, 2);
    String typicalChildrenName = parentAndChildren.get(0).getChildren().get(0).getName().substring(0, 5);

    // WHEN:
    //  This is the only difference from the above test case
    //  `whenFindChildrenByIds_TheLazyLoadedParents_WillAlsoBeReturned().
    //  However, the interesting thing is: the number of SQL executions are different!!!
    //  Please take a look at the log message.
    log.info("When finding children by name '{}'...", typicalChildrenName);
    List<ChildWithLazyLoadEntity> childrenInDB = childService.findByNameContaining(typicalChildrenName);

    // THEN:
    log.info("Assertions...");
    Assertions.assertTrue(!childrenInDB.isEmpty());
    for (ChildWithLazyLoadEntity childEntityInDB : childrenInDB) {
      Assertions.assertNotNull(childEntityInDB.getParentEntity().getId());

      Assertions.assertThrows(LazyInitializationException.class, () -> {
        // The reason for getting LazyInitializationException was already explained in the first test case.
        childEntityInDB.getParentEntity().getName();
      });
    }
  }

  @Test
  public void when_FindChildrenByName_AndAlsoLazyLoadParents_then_NoExceptionIsThrown() {
    // GIVEN:
    List<ParentAndChildrenWithLazyLoad> parentAndChildren = fixtures.createParentsAndChildren(3, 2);
    String typicalChildrenName = parentAndChildren.get(0).getChildren().get(0).getName().substring(0, 5);

    // WHEN:

    log.info("When find children by name '{}'...", typicalChildrenName);
    List<ChildWithLazyLoadEntity> childrenInDB = childService.findChildrenByNameContaining_AndLazyLoadParent(typicalChildrenName);

    // THEN:
    log.info("Assertions...");
    Assertions.assertTrue(!childrenInDB.isEmpty());
    for (ChildWithLazyLoadEntity childEntityInDB : childrenInDB) {
      Assertions.assertNotNull(childEntityInDB.getParentEntity().getId());

      // As we see, there's no exception anymore.
      Assertions.assertNotNull(childEntityInDB.getParentEntity().getName());
    }
  }
}