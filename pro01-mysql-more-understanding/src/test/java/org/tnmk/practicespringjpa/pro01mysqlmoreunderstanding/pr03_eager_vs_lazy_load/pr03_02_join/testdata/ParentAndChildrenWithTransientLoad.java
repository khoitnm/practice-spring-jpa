package org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr03_eager_vs_lazy_load.pr03_02_join.testdata;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr03_eager_vs_lazy_load.common.ParentEntity;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr03_eager_vs_lazy_load.pr03_02_join.ChildWithTransientParentEntity;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class ParentAndChildrenWithTransientLoad {
  private final ParentEntity parent;
  private final List<ChildWithTransientParentEntity> children;
}
