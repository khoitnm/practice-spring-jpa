package org.tnmk.practicespringjpa.pro01simpletimestampfields.sample.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "sample_entity", catalog = "sample_db")
public class SampleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "sample_entity_id")
    private Long id;

    @Column(name = "creation_dateTime", updatable = false, columnDefinition = "timestamp default CURRENT_TIMESTAMP")
    @CreationTimestamp
    private Instant createdDateTime;

    @Column(name = "update_dateTime", columnDefinition = "timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    @UpdateTimestamp
    private Instant updateDateTime;

    private String name;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(Instant createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public Instant getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(Instant updateDateTime) {
        this.updateDateTime = updateDateTime;
    }
}
