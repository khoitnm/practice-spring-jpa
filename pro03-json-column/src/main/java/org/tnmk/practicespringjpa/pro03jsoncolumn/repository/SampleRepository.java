package org.tnmk.practicespringjpa.pro03jsoncolumn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tnmk.practicespringjpa.pro03jsoncolumn.entity.SampleEntity;

public interface SampleRepository extends JpaRepository<SampleEntity, Long> {
}
