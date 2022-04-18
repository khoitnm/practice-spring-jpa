package org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr00_00_updateBySaveEntity_noTnx;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.common.SimpleEntity;

import java.util.Optional;

public interface Pr00_00_SimpleRepository extends JpaRepository<SimpleEntity, Long> {
    Optional<SimpleEntity> findByName(String name);
}
