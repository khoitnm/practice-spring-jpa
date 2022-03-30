package org.tnmk.practicespringjpa.pro01simplemysql.sample_business;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SimpleRepository extends JpaRepository<SimpleEntity, Long> {
}
