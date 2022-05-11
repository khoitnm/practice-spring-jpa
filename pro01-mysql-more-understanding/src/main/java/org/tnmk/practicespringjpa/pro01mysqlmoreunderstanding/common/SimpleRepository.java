package org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.common;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SimpleRepository extends JpaRepository<SimpleEntity, Long> {

  Page<SimpleEntity> findByNameContaining(String name, Pageable pageable);
}
