package org.tnmk.practicespringjpa.pro02onetomany.sample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.tnmk.practicespringjpa.pro02onetomany.sample.entity.ChildEntity;
import org.tnmk.practicespringjpa.pro02onetomany.sample.entity.ParentEntity;

import java.util.List;

public interface ChildRepository extends JpaRepository<ChildEntity, Long> {

}
