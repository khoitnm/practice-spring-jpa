package org.tnmk.practicespringjpa.pro01simplemssql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tnmk.practicespringjpa.pro01simplemssql.entity.SampleEntity;

public interface SampleRepository extends JpaRepository<SampleEntity, Long> {
}
