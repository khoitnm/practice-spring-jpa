package org.tnmk.practicespringjpa.pro00hsqlsimple.sample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.tnmk.practicespringjpa.pro00hsqlsimple.sample.entity.SampleEntity;

import javax.transaction.Transactional;

public interface SampleRepository extends JpaRepository<SampleEntity, Long> {
  @Modifying
  @Transactional
  void deleteByName(String name);
}
