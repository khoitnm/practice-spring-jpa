package org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr03_eager_loading;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChildRepository extends JpaRepository<ChildEntity, Long> {
  List<ChildEntity> findByIdIn(List<Long> ids);

  List<ChildEntity> findByNameContaining(String name);
}
