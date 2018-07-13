package org.tnmk.practicespringjpa.samplebusiness.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tnmk.practicespringjpa.samplebusiness.entity.SampleEntity;

public interface SampleRepository extends JpaRepository<SampleEntity, Long> {
}
