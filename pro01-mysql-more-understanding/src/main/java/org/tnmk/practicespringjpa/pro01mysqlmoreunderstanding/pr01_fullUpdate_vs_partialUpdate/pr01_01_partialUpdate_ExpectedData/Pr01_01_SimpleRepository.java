package org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr01_fullUpdate_vs_partialUpdate.pr01_01_partialUpdate_ExpectedData;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.common.SimpleEntity;

import javax.transaction.Transactional;

@Repository
public interface Pr01_01_SimpleRepository extends JpaRepository<SimpleEntity, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE SimpleEntity se SET se.enabled = false WHERE se.id = :id")
    void disabledEntityById(@Param("id") long entityId);

    @Transactional
    @Modifying
    @Query("UPDATE SimpleEntity se SET se.name = :name WHERE se.id = :id")
    void updateName(@Param("id") long entityId, @Param("name") String name);
}
