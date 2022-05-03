package org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr03_eager_loading.testdata;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr03_eager_loading.ChildEntity;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr03_eager_loading.ChildRepository;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr03_eager_loading.ParentEntity;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr03_eager_loading.ParentRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class ParentAndChildrenFixtures {
  private final ChildRepository childRepository;
  private final ParentRepository parentRepository;

  public List<ParentAndChildren> createParentsAndChildren(int parentsCount, int childrenCountPerParent) {
    List<ParentAndChildren> result = IntStream.range(0, parentsCount)
        .mapToObj(i -> createParentAndChild("Parent " + i, childrenCountPerParent))
        .collect(Collectors.toList());
    return result;
  }

  public ParentAndChildren createParentAndChild(String parentName, int childrenCountPerParent) {
    final ParentEntity parent = new ParentEntity(parentName);
    parentRepository.save(parent);

    List<ChildEntity> children = IntStream.range(0, childrenCountPerParent).mapToObj(i ->
        createChild(parent, "Child " + i + " of '" + parent.getName() + "'")
    ).collect(Collectors.toList());

    return new ParentAndChildren(parent, children);
  }

  public ChildEntity createChild(ParentEntity parent, String childName) {
    ChildEntity child = new ChildEntity(childName);
    child.setParentEntity(parent);
    return childRepository.save(child);
  }

}
