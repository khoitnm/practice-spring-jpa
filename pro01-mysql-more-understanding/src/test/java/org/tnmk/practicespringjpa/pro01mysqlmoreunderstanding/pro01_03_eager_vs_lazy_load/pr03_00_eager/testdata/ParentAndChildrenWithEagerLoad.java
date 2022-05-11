package org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pro01_03_eager_vs_lazy_load.pr03_00_eager.testdata;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pro01_03_eager_vs_lazy_load.common.ParentEntity;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pro01_03_eager_vs_lazy_load.pr03_00_eager.ChildWithEagerLoadEntity;

import java.util.List;
@RequiredArgsConstructor
@Getter
public class ParentAndChildrenWithEagerLoad {
  private final ParentEntity parent;
  private final List<ChildWithEagerLoadEntity> children;
}
