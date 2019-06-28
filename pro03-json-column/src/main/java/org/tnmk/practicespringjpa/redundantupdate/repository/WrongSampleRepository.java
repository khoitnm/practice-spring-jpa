package org.tnmk.practicespringjpa.redundantupdate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tnmk.practicespringjpa.redundantupdate.entity.WrongSampleEntity;

public interface WrongSampleRepository extends JpaRepository<WrongSampleEntity, Long> {
}
