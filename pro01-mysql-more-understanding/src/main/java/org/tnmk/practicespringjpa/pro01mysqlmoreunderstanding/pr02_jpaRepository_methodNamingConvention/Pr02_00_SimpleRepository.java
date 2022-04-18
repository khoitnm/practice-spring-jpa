package org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr02_jpaRepository_methodNamingConvention;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.common.SimpleEntity;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface Pr02_00_SimpleRepository extends JpaRepository<SimpleEntity, Long> {

    @Transactional
    void deleteByNameIsLike(String name);

    List<SimpleEntity> findByName(String expectName);
}
