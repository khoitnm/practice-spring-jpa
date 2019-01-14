package org.tnmk.practicespringjpa.pro01simpleentity.sample.entity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "another_entity", catalog = "sample_db")
public class AnotherEntity {
    @Id
    @Column(name = "another_entity_id")
    private UUID anotherEntityId;

    @OneToOne(mappedBy = "anotherEntity")
    private SampleEntity sampleEntity;

    public UUID getAnotherEntityId() {
        return anotherEntityId;
    }

    public void setAnotherEntityId(UUID anotherEntityId) {
        this.anotherEntityId = anotherEntityId;
    }

    public SampleEntity getSampleEntity() {
        return sampleEntity;
    }

    public void setSampleEntity(SampleEntity sampleEntity) {
        this.sampleEntity = sampleEntity;
    }
}
