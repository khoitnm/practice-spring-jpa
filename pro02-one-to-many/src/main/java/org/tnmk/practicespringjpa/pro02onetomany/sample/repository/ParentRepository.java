package org.tnmk.practicespringjpa.pro02onetomany.sample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.tnmk.practicespringjpa.pro02onetomany.sample.entity.ParentEntity;

import java.util.List;

public interface ParentRepository extends JpaRepository<ParentEntity, Long> {

    @Query(value = "SELECT parentEntity FROM ParentEntity parentEntity LEFT JOIN FETCH parentEntity.children WHERE parentEntity.parentId = :parentId")
    ParentEntity findParentAndChildrenByParentId(@Param("parentId") Long parentId);

    @Query(value = "SELECT parentEntity FROM ParentEntity parentEntity LEFT JOIN FETCH parentEntity.children WHERE parentEntity.name LIKE :parentName")
    List<ParentEntity> findParentsAndChildrenByName(@Param("parentName") String parentName);

    @Query(value = "SELECT parentEntity FROM ParentEntity parentEntity LEFT JOIN FETCH parentEntity.children")
    List<ParentEntity> findAllParentsAndChildren();
}
