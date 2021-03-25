package org.tnmk.practicespringjpa.pro07multitenant.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

/**
 * NOTE that we can have 2 entities classes associated to the same table.
 */
@Entity
@Table(name = "sample_entity"
        /**
         * This name must match with DB name (in application.yml, docker.yml, and testing EmbeddedDBStarter)
        */
        , catalog = "practice_spring_jpa_db"
)
@Setter
@Getter
public class SampleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    private String name;

    @Column(name = "creation_dateTime", updatable = false, columnDefinition = "TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP(6)")
    @CreationTimestamp
    private Instant createdDateTime;

    @Column(name = "update_dateTime", columnDefinition = "TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6)")
    @UpdateTimestamp
    private Instant updateDateTime;
}
