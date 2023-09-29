package org.tnmk.practicespringjpa.pro00mssql04combinequerymodel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.tnmk.practicespringjpa.pro00mssql04combinequerymodel.entity.CombinedModel;
import org.tnmk.practicespringjpa.pro00mssql04combinequerymodel.entity.ParentEntity;
import org.tnmk.practicespringjpa.pro00mssql04combinequerymodel.entity.SampleEntity;

import java.util.List;

public interface ParentRepository extends JpaRepository<ParentEntity, Long> {

}
