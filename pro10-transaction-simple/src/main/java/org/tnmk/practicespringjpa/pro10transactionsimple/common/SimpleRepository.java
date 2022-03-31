package org.tnmk.practicespringjpa.pro10transactionsimple.common;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SimpleRepository extends JpaRepository<SimpleEntity, Long> {
  Optional<SimpleEntity> findByName(String entityName);
}
