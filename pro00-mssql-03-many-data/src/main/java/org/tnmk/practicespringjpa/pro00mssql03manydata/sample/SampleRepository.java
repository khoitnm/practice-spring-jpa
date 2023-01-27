package org.tnmk.practicespringjpa.pro00mssql03manydata.sample;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SampleRepository extends JpaRepository<SampleEntity, Long> {
  Optional<SampleEntity> findByEntityCode(String entityCode);
}
