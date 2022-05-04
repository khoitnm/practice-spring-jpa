package org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr03_eager_vs_lazy_load.pr03_01_lazy;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr03_eager_vs_lazy_load.pr03_01_lazy.testdata.ParentAndChildrenWithLazyLoad;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr03_eager_vs_lazy_load.pr03_01_lazy.testdata.ParentAndChildrenWithLazyLoadFixtures;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.testinfra.BaseSpringTest_WithActualDb;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class Pr03_01_LazyLoadingTest extends BaseSpringTest_WithActualDb {

  @Autowired
  private ParentAndChildrenWithLazyLoadFixtures fixtures;
  @Autowired
  private ChildWithLazyLoadService childService;

  @AfterEach
  public void cleanUp() {
    fixtures.cleanUpAllParentsAndChildren();
  }

  @Test
  public void when_FindChildById_then_CannotLazyLoadParent() {
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
  public void when_FindChildrenByIds_then_CannotLazyLoadParent() {
    // GIVEN:
    int parentsCount = 3;
    int childrenCountPerParent = 2;
    List<ParentAndChildrenWithLazyLoad> parentAndChildren = fixtures.createParentsAndChildren(parentsCount, childrenCountPerParent);
    List<Long> childrenIds = getAllChildrenIds(parentAndChildren);

    // WHEN:
    //  As we can guess, the number of SQL statements are executed is different from
    //  the previous test case `when_FindChildById_then_TheLazyLoadedParent_WillAlsoBeReturned()`
    log.info("When finding children by ids {}...", childrenIds);
    List<ChildWithLazyLoadEntity> childrenInDB = childService.findByIds(childrenIds);

    // THEN:
    log.info("Assertions...");
    Assertions.assertEquals(parentsCount * childrenCountPerParent, childrenInDB.size());
    for (ChildWithLazyLoadEntity childEntityInDB : childrenInDB) {
      Assertions.assertNotNull(childEntityInDB.getParentEntity().getId());

      Assertions.assertThrows(LazyInitializationException.class, () -> {
        // The reason for getting LazyInitializationException was already explained in the first test case.
        childEntityInDB.getParentEntity().getName();
      });
    }
  }

  @Test
  public void when_FindChildrenByName_then_CannotLazyLoadParent() {
    // GIVEN:
    int parentsCount = 3;
    int childrenCountPerParent = 2;
    List<ParentAndChildrenWithLazyLoad> parentAndChildren = fixtures.createParentsAndChildren(parentsCount, childrenCountPerParent);
    String aPartOfChildrenName = ParentAndChildrenWithLazyLoadFixtures.CHILD_NAME_PREFIX;

    // WHEN:
    //  This is the only difference from the above test case
    //  `whenFindChildrenByIds_TheLazyLoadedParents_WillAlsoBeReturned().
    //  However, the interesting thing is: the number of SQL executions are different!!!
    //  Please take a look at the log message.
    log.info("When finding children by name '{}'...", aPartOfChildrenName);
    List<ChildWithLazyLoadEntity> childrenInDB = childService.findByNameContaining(aPartOfChildrenName);

    // THEN:
    log.info("Assertions...");
    Assertions.assertEquals(parentsCount * childrenCountPerParent, childrenInDB.size());
    for (ChildWithLazyLoadEntity childEntityInDB : childrenInDB) {
      Assertions.assertNotNull(childEntityInDB.getParentEntity().getId());

      Assertions.assertThrows(LazyInitializationException.class, () -> {
        // The reason for getting LazyInitializationException was already explained in the first test case.
        childEntityInDB.getParentEntity().getName();
      });
    }
  }

  @Test
  public void when_FindChildrenByNameContaining_AndLazyLoadParentInTnx_then_NoExceptionIsThrown() {
    // GIVEN:
    int parentsCount = 3;
    int childrenCountPerParent = 2;
    List<ParentAndChildrenWithLazyLoad> parentAndChildren = fixtures.createParentsAndChildren(parentsCount, childrenCountPerParent);
    String aPartOfChildrenName = ParentAndChildrenWithLazyLoadFixtures.CHILD_NAME_PREFIX;

    // WHEN:
    log.info("When find children by name '{}'...", aPartOfChildrenName);
    /**
     * The difference from {@link ChildWithLazyLoadService#findByNameContaining(String)} is:
     * we also won't get exception when we access ParentEntity's fields from the result of this method from outside the service.
     * However, we get N+1 problem here.
     */
    List<ChildWithLazyLoadEntity> childrenInDB = childService.findByNameContaining_AndLazyLoadParentInTnx(aPartOfChildrenName);

    // THEN:
    log.info("Assertions...");
    Assertions.assertEquals(parentsCount * childrenCountPerParent, childrenInDB.size());
    for (ChildWithLazyLoadEntity childEntityInDB : childrenInDB) {
      Assertions.assertNotNull(childEntityInDB.getParentEntity().getId());

      // As mentioned in the above comment, this method also won't get LazyInitializationException.
      Assertions.assertNotNull(childEntityInDB.getParentEntity().getName());
    }
  }

  @Test
  public void when_FindChildrenByNameContaining_AndJoinParent_then_NoExceptionIsThrown() {
    // GIVEN:
    List<ParentAndChildrenWithLazyLoad> parentAndChildren = fixtures.createParentsAndChildren(3, 2);
    String aPartOfChildrenName = ParentAndChildrenWithLazyLoadFixtures.CHILD_NAME_PREFIX;

    // WHEN:
    log.info("When finding children by name '{}'...", aPartOfChildrenName);
    /**
     * The same as {@link ChildWithLazyLoadService#findByNameContaining_AndLazyLoadParentInTnx(String)},
     * we also won't get exception when we access ParentEntity's fields from the result of this method from outside the service.
     * The difference is by joining, the amount of SQL statements are executed by Hibernate is only one.
     */
    List<ChildWithLazyLoadEntity> childrenInDB = childService.findByNameContaining_AndJoinParent(aPartOfChildrenName);

    // THEN:
    log.info("Assertions...");
    Assertions.assertTrue(!childrenInDB.isEmpty());
    for (ChildWithLazyLoadEntity childEntityInDB : childrenInDB) {
      Assertions.assertNotNull(childEntityInDB.getParentEntity().getId());

      // As mentioned in the above comment, this method also won't get LazyInitializationException.
      Assertions.assertNotNull(childEntityInDB.getParentEntity().getName());
    }
  }

  private static List<Long> getAllChildrenIds(List<ParentAndChildrenWithLazyLoad> parentsAndChildren) {
    List<Long> childrenIds = parentsAndChildren.stream().flatMap(
        parentAndChildrenItem -> toChildrenIds(parentAndChildrenItem.getChildren()).stream()
    ).collect(Collectors.toList());
    return childrenIds;
  }

  private static List<Long> toChildrenIds(List<ChildWithLazyLoadEntity> children) {
    return children.stream().map(ChildWithLazyLoadEntity::getId).collect(Collectors.toList());
  }
}