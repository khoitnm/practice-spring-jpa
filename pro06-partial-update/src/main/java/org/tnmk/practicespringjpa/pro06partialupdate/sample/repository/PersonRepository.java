package org.tnmk.practicespringjpa.pro06partialupdate.sample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.tnmk.practicespringjpa.pro06partialupdate.sample.entity.PersonEntity;

public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

  @Modifying
  @Query("update PersonEntity p set p.fullName = :fullName where p.id = :id")
  int updateProfile(@Param(value = "id") long id, @Param(value = "fullName") String fullName);
}
