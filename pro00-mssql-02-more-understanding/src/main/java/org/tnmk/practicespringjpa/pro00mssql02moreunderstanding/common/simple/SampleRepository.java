package org.tnmk.practicespringjpa.pro00mssql02moreunderstanding.common.simple;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SampleRepository extends JpaRepository<SampleEntity, Long> {
}
