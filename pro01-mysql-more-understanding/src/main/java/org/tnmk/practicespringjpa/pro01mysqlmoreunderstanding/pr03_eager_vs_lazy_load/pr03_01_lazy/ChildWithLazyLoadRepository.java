package org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr03_eager_vs_lazy_load.pr03_01_lazy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChildWithLazyLoadRepository extends JpaRepository<ChildWithLazyLoadEntity, Long> {
  List<ChildWithLazyLoadEntity> findByNameContaining(@Param("name") String name);

  @Query("SELECT c, p FROM ChildWithLazyLoadEntity c LEFT JOIN FETCH c.parentEntity p WHERE c.name LIKE %:childName%")
  List<ChildWithLazyLoadEntity> findByChildNameContaining_AndJoinParent(@Param("childName") String childName);
}
