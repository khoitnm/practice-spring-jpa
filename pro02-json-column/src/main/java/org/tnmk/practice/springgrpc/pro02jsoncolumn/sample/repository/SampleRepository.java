package org.tnmk.practice.springgrpc.pro02jsoncolumn.sample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tnmk.practice.springgrpc.pro02jsoncolumn.sample.entity.SampleEntity;

public interface SampleRepository extends JpaRepository<SampleEntity, Long> {
}
