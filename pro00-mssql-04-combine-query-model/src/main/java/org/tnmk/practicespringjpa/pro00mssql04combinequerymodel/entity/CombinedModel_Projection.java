package org.tnmk.practicespringjpa.pro00mssql04combinequerymodel.entity;

import org.springframework.beans.factory.annotation.Value;

import java.time.Instant;


//@NoArgsConstructor
//@Getter
//@Setter
//@ToString

/**
 * In this approach, we are using Spring Data Projection:
 * https://docs.spring.io/spring-data/mongodb/reference/data-commons/repositories/projections.html
 * https://www.baeldung.com/spring-data-jpa-projections
 */
public interface CombinedModel_Projection {
    @Value("#{id}")
    Long getId();

    @Value("#{name}")
    String getName();

    @Value("#{target.parent.id}")
    Long getParentId();

    @Value("#{target.parent.name}")
    String getParentName();

    @Value("#{target.startingDateTime}")
    Instant getStartingDateTime();
}
