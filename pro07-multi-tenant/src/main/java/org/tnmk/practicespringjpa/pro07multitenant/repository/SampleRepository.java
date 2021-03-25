package org.tnmk.practicespringjpa.pro07multitenant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tnmk.practicespringjpa.pro07multitenant.entity.SampleEntity;

public interface SampleRepository extends JpaRepository<SampleEntity, Long> {
}
