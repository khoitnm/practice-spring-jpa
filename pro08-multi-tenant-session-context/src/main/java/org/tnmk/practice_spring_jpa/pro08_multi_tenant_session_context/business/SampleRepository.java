package org.tnmk.practice_spring_jpa.pro08_multi_tenant_session_context.business;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SampleRepository extends JpaRepository<SampleEntity, Long> {
}
