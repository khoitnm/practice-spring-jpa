package org.tnmk.practicespringjpa.pro07multitenant.correctimplementation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tnmk.practicespringjpa.pro07multitenant.correctimplementation.entity.SampleEntity;

public interface SampleRepository extends JpaRepository<SampleEntity, Long> {
}
