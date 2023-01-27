package org.tnmk.practicespringjpa.pro00mssql03manydata.sample;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SampleRepository extends JpaRepository<SampleEntity, Long> {
  Optional<SampleEntity> findByEntityCode(String entityCode);

  @Query("SELECT se FROM SampleEntity se")
  List<SampleEntity> findAllItems();
}
