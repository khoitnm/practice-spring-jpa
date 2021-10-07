package org.tnmk.practicespringjpa.pro08mssqlsimple.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tnmk.practicespringjpa.pro08mssqlsimple.entity.SampleEntity;

public interface SampleRepository extends JpaRepository<SampleEntity, Long> {
}
