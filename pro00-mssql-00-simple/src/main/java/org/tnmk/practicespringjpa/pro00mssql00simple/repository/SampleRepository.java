package org.tnmk.practicespringjpa.pro00mssql00simple.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tnmk.practicespringjpa.pro00mssql00simple.entity.SampleEntity;

public interface SampleRepository extends JpaRepository<SampleEntity, Long> {
}
