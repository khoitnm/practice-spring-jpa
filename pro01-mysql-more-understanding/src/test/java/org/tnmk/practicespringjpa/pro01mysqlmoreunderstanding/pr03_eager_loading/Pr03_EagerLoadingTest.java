package org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr03_eager_loading;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.testinfra.BaseSpringTest_WithActualDb;

public class Pr03_EagerLoadingTest extends BaseSpringTest_WithActualDb {

  @Autowired
  private ChildRepository childRepository;

  @Autowired
  private ParentRepository parentRepository;

  @Autowired
  private ChildService childService;

  @Test
  public void test_whenSelectingChild_TheEagerLoadedParent_WillAlsoBeReturned() {
    // GIVEN:
    ParentEntity parent = new ParentEntity("parent");
    ChildEntity child = new ChildEntity("child");

    parent = parentRepository.save(parent);

    child.setParentEntity(parent);
    child = childRepository.save(child);

    // WHEN:
    // When looking at the log messages, because ParentEntity is eager loaded along with ChildEntity,
    // we should see only one `SELECT ... FROM child_entity LEFT OUTER JOIN parent ...`
    ChildEntity childEntityInDB = childService.findChildById(child.getId()).get();

    // THEN:
    Assertions.assertEquals(parent.getName(), childEntityInDB.getParentEntity().getName());
  }
}
