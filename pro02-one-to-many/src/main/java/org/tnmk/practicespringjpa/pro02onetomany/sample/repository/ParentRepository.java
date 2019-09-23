package org.tnmk.practicespringjpa.pro02onetomany.sample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tnmk.practicespringjpa.pro02onetomany.sample.entity.ParentEntity;

public interface ParentRepository extends JpaRepository<ParentEntity, Long> {

    ParentEntity findParentAndChildrenByParentId(Long parentId);
}
