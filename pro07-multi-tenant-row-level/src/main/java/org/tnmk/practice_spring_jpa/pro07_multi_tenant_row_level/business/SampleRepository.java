package org.tnmk.practice_spring_jpa.pro07_multi_tenant_row_level.business;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SampleRepository extends JpaRepository<SampleEntity, Long> {
}
