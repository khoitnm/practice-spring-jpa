package org.tnmk.practicespringjpa.wrongimplementation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tnmk.practicespringjpa.wrongimplementation.entity.SampleWithWrongChildEntity;

public interface SampleWithWrongChildRepository extends JpaRepository<SampleWithWrongChildEntity, Long> {
}
