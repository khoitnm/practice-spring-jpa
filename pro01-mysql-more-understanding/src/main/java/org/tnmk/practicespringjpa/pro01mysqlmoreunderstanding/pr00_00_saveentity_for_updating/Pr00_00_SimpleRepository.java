package org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr00_00_saveentity_for_updating;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.common.SimpleEntity;

public interface Pr00_00_SimpleRepository extends JpaRepository<SimpleEntity, Long> {
}
