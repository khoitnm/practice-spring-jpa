package org.tnmk.practicespringjpa.pro06partialupdate.sample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.tnmk.practicespringjpa.pro06partialupdate.sample.entity.PersonEntity;

public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

  /**
   * This is an example of updating a simple object
   * @param id
   * @param fullName
   * @return
   */
  @Modifying
  @Query("update PersonEntity p set p.fullName = :fullName, p.email = :email where p.id = :id")
  int updateProfile(@Param(value = "id") long id, @Param(value = "fullName") String fullName, @Param(value = "email") String email);
}
