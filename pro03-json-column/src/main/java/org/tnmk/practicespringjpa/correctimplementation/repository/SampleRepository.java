package org.tnmk.practicespringjpa.correctimplementation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tnmk.practicespringjpa.correctimplementation.entity.SampleEntity;

public interface SampleRepository extends JpaRepository<SampleEntity, Long> {
}
