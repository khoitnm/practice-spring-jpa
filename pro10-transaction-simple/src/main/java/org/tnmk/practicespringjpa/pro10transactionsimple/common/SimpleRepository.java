package org.tnmk.practicespringjpa.pro10transactionsimple.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SimpleRepository extends JpaRepository<SimpleEntity, Long> {
  Optional<SimpleEntity> findByName(String entityName);

  @Query("UPDATE SimpleEntity e SET e.name = :name WHERE e.id = :id")
  @Modifying
  void updateNameById(@Param("name") String newName,@Param("id") Long id);
}
