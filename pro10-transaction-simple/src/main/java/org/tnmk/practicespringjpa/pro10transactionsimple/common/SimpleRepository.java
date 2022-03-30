package org.tnmk.practicespringjpa.pro10transactionsimple.common;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SimpleRepository extends JpaRepository<SimpleEntity, Long> {
}
