package org.tnmk.practicespringjpa.pro10transaction.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SimpleRepository extends JpaRepository<SimpleEntity, Long> {
    Optional<SimpleEntity> findByName(String entityName);

    @Query("UPDATE SimpleEntity e SET e.name = :name WHERE e.id = :id")
    @Modifying
    void updateNameById(@Param("name") String newName, @Param("id") Long id);

    @Query(value = "INSERT INTO simple_entity(name) VALUES (:name)", nativeQuery = true)
    @Modifying
    void insertName(@Param("name") String newName);
}
