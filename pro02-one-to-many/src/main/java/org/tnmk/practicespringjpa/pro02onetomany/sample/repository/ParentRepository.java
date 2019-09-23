package org.tnmk.practicespringjpa.pro02onetomany.sample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.tnmk.practicespringjpa.pro02onetomany.sample.entity.ParentEntity;

import java.util.List;

public interface ParentRepository extends JpaRepository<ParentEntity, Long> {

    /**
     * Must use LEFT JOIN. Otherwise, the result will be null if the Parent doesn't have any Child.
     * @param parentId
     * @return
     */
    @Query(value = "SELECT parentEntity FROM ParentEntity parentEntity LEFT JOIN FETCH parentEntity.children WHERE parentEntity.parentId = :parentId")
    ParentEntity findParentAndChildrenByParentId(@Param("parentId") Long parentId);

    /**
     * NOTE:
     * Must add DISTINCT. Otherwise, it will return duplicate parentEntity if that parentEntity has many children.
     * @param parentName
     * @return
     */
    @Query(value = "SELECT DISTINCT parentEntity FROM ParentEntity parentEntity LEFT JOIN FETCH parentEntity.children WHERE parentEntity.name LIKE CONCAT('%',:parentName,'%')")
    List<ParentEntity> findParentsAndChildrenByLikeParentName(@Param("parentName") String parentName);

    @Query(value = "SELECT DISTINCT parentEntity FROM ParentEntity parentEntity LEFT JOIN FETCH parentEntity.children")
    List<ParentEntity> findAllParentsAndChildren();
}
