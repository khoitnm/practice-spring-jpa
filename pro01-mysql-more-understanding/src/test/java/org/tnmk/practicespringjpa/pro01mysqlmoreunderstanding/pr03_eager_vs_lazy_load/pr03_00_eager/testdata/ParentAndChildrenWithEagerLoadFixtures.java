package org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr03_eager_vs_lazy_load.pr03_00_eager.testdata;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr03_eager_vs_lazy_load.common.ParentEntity;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr03_eager_vs_lazy_load.common.ParentRepository;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr03_eager_vs_lazy_load.pr03_00_eager.ChildWithEagerLoadEntity;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr03_eager_vs_lazy_load.pr03_00_eager.ChildWithEagerLoadRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class ParentAndChildrenWithEagerLoadFixtures {
  public static final String PARENT_NAME_PREFIX = "Parent ";
  public static final String CHILD_NAME_PREFIX = "Child ";
  private final ChildWithEagerLoadRepository childRepository;
  private final ParentRepository parentRepository;

  public List<ParentAndChildrenWithEagerLoad> createParentsAndChildren(int parentsCount, int childrenCountPerParent) {
    List<ParentAndChildrenWithEagerLoad> result = IntStream.range(0, parentsCount)
        .mapToObj(i -> createParentAndChild(PARENT_NAME_PREFIX + i, childrenCountPerParent))
        .collect(Collectors.toList());
    return result;
  }

  public ParentAndChildrenWithEagerLoad createParentAndChild(String parentName, int childrenCountPerParent) {
    final ParentEntity parent = new ParentEntity(parentName);
    parentRepository.save(parent);

    List<ChildWithEagerLoadEntity> children = IntStream.range(0, childrenCountPerParent).mapToObj(i ->
        createChild(parent, CHILD_NAME_PREFIX + i + " of '" + parent.getName() + "'")
    ).collect(Collectors.toList());

    return new ParentAndChildrenWithEagerLoad(parent, children);
  }

  public ChildWithEagerLoadEntity createChild(ParentEntity parent, String childName) {
    ChildWithEagerLoadEntity child = new ChildWithEagerLoadEntity(childName);
    child.setParentEntity(parent);
    return childRepository.save(child);
  }

  @Transactional
  public void cleanUpAllParentsAndChildren() {
    childRepository.deleteAll();
    parentRepository.deleteAll();
  }
}
