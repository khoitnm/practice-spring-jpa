package org.tnmk.practicespringjpa.pro09hirakiconnectionpool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tnmk.practicespringjpa.pro09hirakiconnectionpool.entity.SampleEntity;

public interface SampleRepository extends JpaRepository<SampleEntity, Long> {
}
