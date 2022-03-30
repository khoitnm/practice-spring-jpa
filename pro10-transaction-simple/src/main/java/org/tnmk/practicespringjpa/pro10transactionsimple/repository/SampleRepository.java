package org.tnmk.practicespringjpa.pro10transactionsimple.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tnmk.practicespringjpa.pro10transactionsimple.entity.SampleEntity;

public interface SampleRepository extends JpaRepository<SampleEntity, Long> {
}
