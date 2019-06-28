package org.tnmk.practicespringjpa.redundantupdate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tnmk.practicespringjpa.redundantupdate.entity.SampleWithWrongChildEntity;

public interface SampleWithWrongChildRepository extends JpaRepository<SampleWithWrongChildEntity, Long> {
}
