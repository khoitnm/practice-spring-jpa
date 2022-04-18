package org.tnmk.practicespringjpa.pro00mssqlsimple.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tnmk.practicespringjpa.pro00mssqlsimple.entity.SampleEntity;

public interface SampleRepository extends JpaRepository<SampleEntity, Long> {
}
