package org.tnmk.practicespringjpa.noredundantupdate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tnmk.practicespringjpa.noredundantupdate.entity.SampleEntity;

public interface SampleRepository extends JpaRepository<SampleEntity, Long> {
}
