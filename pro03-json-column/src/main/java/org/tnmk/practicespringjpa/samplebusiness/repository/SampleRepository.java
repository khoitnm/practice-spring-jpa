package org.tnmk.practicespringjpa.samplebusiness.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tnmk.practicespringjpa.samplebusiness.entity.ParentEntity;

public interface SampleRepository extends JpaRepository<ParentEntity, Long> {
}
