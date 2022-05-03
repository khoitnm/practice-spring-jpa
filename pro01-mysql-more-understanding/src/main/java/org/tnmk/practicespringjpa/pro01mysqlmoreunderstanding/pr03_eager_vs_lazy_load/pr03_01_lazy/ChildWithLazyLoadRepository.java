package org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr03_eager_vs_lazy_load.pr03_01_lazy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChildWithLazyLoadRepository extends JpaRepository<ChildWithLazyLoadEntity, Long> {
  List<ChildWithLazyLoadEntity> findByIdIn(List<Long> ids);

  List<ChildWithLazyLoadEntity> findByNameContaining(String name);
}
