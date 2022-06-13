package org.tnmk.practicespringjpa.pro00mssql01jdbcbatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tnmk.practicespringjpa.pro00mssql01jdbcbatch.entity.SampleEntity;

public interface SampleRepository extends JpaRepository<SampleEntity, Long> {
}
