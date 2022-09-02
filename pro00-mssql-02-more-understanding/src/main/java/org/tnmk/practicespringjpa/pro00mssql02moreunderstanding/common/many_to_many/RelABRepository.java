package org.tnmk.practicespringjpa.pro00mssql02moreunderstanding.common.many_to_many;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RelABRepository extends JpaRepository<RelAB, Integer> {
  @Modifying
  @Transactional
  @Query("delete from RelAB where entityAId = :aId")
  void deleteByEntityAId(@Param("aId") int aId);

  @Query("select rel from RelAB rel where rel.entityAId = :aId")
  List<RelAB> findByEntityAId(@Param("aId") int aId);
}
