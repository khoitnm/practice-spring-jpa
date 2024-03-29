package org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pro01_03_eager_vs_lazy_load.pr03_01_lazy.testdata;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pro01_03_eager_vs_lazy_load.common.ParentEntity;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pro01_03_eager_vs_lazy_load.common.ParentRepository;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pro01_03_eager_vs_lazy_load.pr03_01_lazy.ChildWithLazyLoadEntity;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pro01_03_eager_vs_lazy_load.pr03_01_lazy.ChildWithLazyLoadRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class ParentAndChildrenWithLazyLoadFixtures {
  public static final String PARENT_NAME_PREFIX = "Parent ";
  public static final String CHILD_NAME_PREFIX = "Child ";
  private final ChildWithLazyLoadRepository childRepository;
  private final ParentRepository parentRepository;

  public List<ParentAndChildrenWithLazyLoad> createParentsAndChildren(int parentsCount, int childrenCountPerParent) {
    List<ParentAndChildrenWithLazyLoad> result = IntStream.range(0, parentsCount)
        .mapToObj(i -> createParentAndChild(PARENT_NAME_PREFIX + i, childrenCountPerParent))
        .collect(Collectors.toList());
    return result;
  }

  public ParentAndChildrenWithLazyLoad createParentAndChild(String parentName, int childrenCountPerParent) {
    final ParentEntity parent = new ParentEntity(parentName);
    parentRepository.save(parent);

    List<ChildWithLazyLoadEntity> children = IntStream.range(0, childrenCountPerParent).mapToObj(i ->
        createChild(parent, CHILD_NAME_PREFIX + i + " of '" + parent.getName() + "'")
    ).collect(Collectors.toList());

    return new ParentAndChildrenWithLazyLoad(parent, children);
  }

  public ChildWithLazyLoadEntity createChild(ParentEntity parent, String childName) {
    ChildWithLazyLoadEntity child = new ChildWithLazyLoadEntity(childName);
    child.setParentEntity(parent);
    return childRepository.save(child);
  }

  @Transactional
  public void cleanUpAllParentsAndChildren() {
    childRepository.deleteAll();
    parentRepository.deleteAll();
  }
}
