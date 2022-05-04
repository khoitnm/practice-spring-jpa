package org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr03_eager_vs_lazy_load.pr03_02_join.testdata;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr03_eager_vs_lazy_load.common.ParentEntity;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr03_eager_vs_lazy_load.common.ParentRepository;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr03_eager_vs_lazy_load.pr03_02_join.ChildWithTransientParentEntity;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr03_eager_vs_lazy_load.pr03_02_join.ChildWithTransientParentRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class ParentAndChildrenWithTransientLoadFixtures {
  private final ChildWithTransientParentRepository childRepository;
  private final ParentRepository parentRepository;

  public List<ParentAndChildrenWithTransientLoad> createParentsAndChildren(int parentsCount, int childrenCountPerParent) {
    List<ParentAndChildrenWithTransientLoad> result = IntStream.range(0, parentsCount)
        .mapToObj(i -> createParentAndChild("Parent " + i, childrenCountPerParent))
        .collect(Collectors.toList());
    return result;
  }

  public ParentAndChildrenWithTransientLoad createParentAndChild(String parentName, int childrenCountPerParent) {
    final ParentEntity parent = new ParentEntity(parentName);
    parentRepository.save(parent);

    List<ChildWithTransientParentEntity> children = IntStream.range(0, childrenCountPerParent).mapToObj(i ->
        createChild(parent, "Child " + i + " of '" + parent.getName() + "'")
    ).collect(Collectors.toList());

    return new ParentAndChildrenWithTransientLoad(parent, children);
  }

  public ChildWithTransientParentEntity createChild(ParentEntity parent, String childName) {
    ChildWithTransientParentEntity child = new ChildWithTransientParentEntity(childName);
    child.setParentEntity(parent);
    return childRepository.save(child);
  }

}
