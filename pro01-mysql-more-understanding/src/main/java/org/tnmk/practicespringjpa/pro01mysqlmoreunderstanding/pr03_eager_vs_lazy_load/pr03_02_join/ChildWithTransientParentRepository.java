package org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr03_eager_vs_lazy_load.pr03_02_join;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChildWithTransientParentRepository extends JpaRepository<ChildWithTransientParentEntity, Long> {

  List<ChildWithTransientParentEntity> findByNameContaining(@Param("name") String name);
  @Query("SELECT c, p FROM ChildWithTransientParentEntity c JOIN c.parentEntity p WHERE c.name LIKE %:childName%")
  List<ChildWithTransientParentEntity> findChildrenJoinParent_ByChildNameContaining(@Param("childName") String childName);
}
