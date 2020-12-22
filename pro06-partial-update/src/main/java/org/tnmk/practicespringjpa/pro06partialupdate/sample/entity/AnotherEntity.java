package org.tnmk.practicespringjpa.pro06partialupdate.sample.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "another_entity", catalog = "sample_db")
public class AnotherEntity {
    @Id
    @Column(name = "another_entity_id")
    private UUID anotherEntityId;


    public UUID getAnotherEntityId() {
        return anotherEntityId;
    }

    public void setAnotherEntityId(UUID anotherEntityId) {
        this.anotherEntityId = anotherEntityId;
    }
}
