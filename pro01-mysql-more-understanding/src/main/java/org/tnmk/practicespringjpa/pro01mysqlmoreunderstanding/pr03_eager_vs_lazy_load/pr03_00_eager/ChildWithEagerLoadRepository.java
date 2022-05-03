package org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr03_eager_vs_lazy_load.pr03_00_eager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChildWithEagerLoadRepository extends JpaRepository<ChildWithEagerLoadEntity, Long> {
  List<ChildWithEagerLoadEntity> findByIdIn(List<Long> ids);

  List<ChildWithEagerLoadEntity> findByNameContaining(String name);
}
