package org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr03_eager_vs_lazy_load.pr03_00_eager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChildWithEagerLoadRepository extends JpaRepository<ChildWithEagerLoadEntity, Long> {
  List<ChildWithEagerLoadEntity> findByNameContaining(String name);

  // Note: because ParentEntity is eager loaded, we must have `parent_id` in selected columns.
  //  Otherwise, we'll get SQLException
  @Query(value = "SELECT DISTINCT id, parent_id, name FROM child WHERE name like %:name%", nativeQuery = true)
  List<ChildWithEagerLoadEntity> findByNameContaining_withNativeQuery(@Param("name") String name);
}
