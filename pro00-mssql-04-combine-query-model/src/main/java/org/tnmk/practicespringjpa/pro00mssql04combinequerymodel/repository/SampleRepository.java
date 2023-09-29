package org.tnmk.practicespringjpa.pro00mssql04combinequerymodel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.tnmk.practicespringjpa.pro00mssql04combinequerymodel.entity.CombinedModel;
import org.tnmk.practicespringjpa.pro00mssql04combinequerymodel.entity.CombinedModel_Projection;
import org.tnmk.practicespringjpa.pro00mssql04combinequerymodel.entity.SampleEntity;

import java.util.List;

public interface SampleRepository extends JpaRepository<SampleEntity, Long> {

    @Query("SELECT new org.tnmk.practicespringjpa.pro00mssql04combinequerymodel.entity.CombinedModel(" +
        " s.id," +
        " s.name," +
        " p.id," +
        " p.name," +
        " s.createdDateTime " +
        ") FROM SampleEntity s JOIN s.parent p")
    List<CombinedModel> getCombinedModels();

    /**
     * Please view {@link CombinedModel_Projection} for more details about this approach.
     * This doesn't work on old Spring Boot framework (2.5.0).
     * So, I'm trying with newer Spring version.
     *
     * I tried with Spring Boot 2.7.16 (which is newest version at 2023/09/29) for Java 11.
     * Didn't work too, maybe it only works with Spring 3.x.x (for Java 17+ only)???
     *
     * Anyway, I just give up this approach.
     */
    @Query("SELECT " +
        " s.id," +
        " s.name," +
        " p.id as parentId," +
        " p.name as parentName," +
        " s.createdDateTime " +
        " FROM SampleEntity s JOIN s.parent p")
    List<CombinedModel_Projection> getCombinedModelsUseProjection();
}
