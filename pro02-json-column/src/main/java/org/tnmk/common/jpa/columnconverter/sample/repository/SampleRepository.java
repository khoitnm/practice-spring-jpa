package org.tnmk.common.jpa.columnconverter.sample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tnmk.common.jpa.columnconverter.sample.entity.SampleEntity;

public interface SampleRepository extends JpaRepository<SampleEntity, Long> {
}
