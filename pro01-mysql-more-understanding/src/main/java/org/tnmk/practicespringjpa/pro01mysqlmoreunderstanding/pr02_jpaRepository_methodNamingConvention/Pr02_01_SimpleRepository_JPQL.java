package org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr02_jpaRepository_methodNamingConvention;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.common.SimpleEntity;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface Pr02_01_SimpleRepository_JPQL extends JpaRepository<SimpleEntity, Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM SimpleEntity se WHERE se.name like :name")
    void deleteByNameIsLike(@Param("name") String name);

    List<SimpleEntity> findByName(String expectName);
}
