package org.tnmk.practicespringjpa.pro00mssql04combinequerymodel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.tnmk.practicespringjpa.pro00mssql04combinequerymodel.entity.CombinedModel;
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

//    @Query("SELECT "
//        + " s.id, s.name, s.parent.id as parentId, s.parent.name as parentName, s.createdDateTime " +
//        " FROM SampleEntity s")
//    List<CombinedModel> getCombinedModels();
}
