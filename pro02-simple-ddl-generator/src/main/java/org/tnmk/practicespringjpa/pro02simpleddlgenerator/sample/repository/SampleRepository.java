package org.tnmk.practicespringjpa.pro02simpleddlgenerator.sample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tnmk.practicespringjpa.pro02simpleddlgenerator.sample.entity.SampleEntity;

public interface SampleRepository extends JpaRepository<SampleEntity, Long> {
}
