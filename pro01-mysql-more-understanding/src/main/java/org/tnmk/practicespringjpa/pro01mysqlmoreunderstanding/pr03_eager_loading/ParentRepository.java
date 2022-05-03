package org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr03_eager_loading;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParentRepository extends JpaRepository<ParentEntity, Long> {
}
