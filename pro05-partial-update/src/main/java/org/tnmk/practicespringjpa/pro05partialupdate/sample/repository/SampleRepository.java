package org.tnmk.practicespringjpa.pro05partialupdate.sample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tnmk.practicespringjpa.pro05partialupdate.sample.entity.SampleEntity;

public interface SampleRepository extends JpaRepository<SampleEntity, Long> {
}
