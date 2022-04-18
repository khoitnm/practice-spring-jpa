package org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimpleRepository extends JpaRepository<SimpleEntity, Long> {
}
