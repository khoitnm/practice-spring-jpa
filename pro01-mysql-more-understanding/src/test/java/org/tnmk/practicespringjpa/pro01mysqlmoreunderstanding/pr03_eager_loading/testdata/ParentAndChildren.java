package org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr03_eager_loading.testdata;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr03_eager_loading.ChildEntity;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr03_eager_loading.ParentEntity;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class ParentAndChildren {
  private final ParentEntity parent;
  private final List<ChildEntity> children;
}
