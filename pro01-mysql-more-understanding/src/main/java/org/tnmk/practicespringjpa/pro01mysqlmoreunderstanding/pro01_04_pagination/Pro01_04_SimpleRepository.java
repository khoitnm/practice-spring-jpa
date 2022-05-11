package org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pro01_04_pagination;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.common.SimpleEntity;

@Repository
public interface Pro01_04_SimpleRepository extends JpaRepository<SimpleEntity, Long> {

  Page<SimpleEntity> findByNameContaining(String name, Pageable pageable);

  @Query("SELECT s FROM SimpleEntity s WHERE s.name LIKE %:name%")
  Page<SimpleEntity> findByNameContaining_JPQL(@Param("name") String name, Pageable pageable);

  @Query(value = "SELECT s.* FROM sample_entity AS s WHERE s.name LIKE %:name%"
      // We have to write this custom count query.
      // Otherwise, it will get error because of generated SQL is "SELECT count(*) FROM ..." which is wrong syntax.
      , countQuery = "SELECT COUNT(s.id) FROM sample_entity AS s WHERE s.name LIKE %:name%"
      , nativeQuery = true)
  Page<SimpleEntity> findByNameContaining_NativeQuery(@Param("name") String name, Pageable pageable);

  @Query(nativeQuery = true, countName = "SimpleEntity.countByNameContaining_NamedNativeQuery.count")
  Page<SimpleEntity> findByNameContaining_NamedNativeQuery(@Param("name") String name, Pageable pageable);
}
