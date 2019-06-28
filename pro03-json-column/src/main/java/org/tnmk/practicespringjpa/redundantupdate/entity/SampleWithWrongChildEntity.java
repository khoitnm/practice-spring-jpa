package org.tnmk.practicespringjpa.redundantupdate.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.tnmk.practicespringjpa.redundantupdate.entity.columnconverter.WrongChildEntityConverter;
import org.tnmk.practicespringjpa.redundantupdate.entity.columnconverter.WrongChildEntityListConverter;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

/**
 *
 */
@Entity
@Table(name = "sample_with_wrong_child_entity"
    /**
     * This name must match with DB name (in application.yml, docker.yml, and testing EmbeddedDBStarter)
     */
    , catalog = "practice_spring_jpa_db"
)
public class SampleWithWrongChildEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "sample_entity_id")
    private Long sampleEntityId;

    private String name;

    @Column(name = "main_child_entity", columnDefinition = "JSON")
    @Convert(converter = WrongChildEntityConverter.class)
    private WrongChildEntity mainWrongChildEntity;

    @Column(name = "other_child_entities", columnDefinition = "JSON")
    @Convert(converter = WrongChildEntityListConverter.class)
    private List<WrongChildEntity> otherChildEntities;

    @Column(name = "creation_dateTime", updatable = false, columnDefinition = "TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP(6)")
    @CreationTimestamp
    private Instant createdDateTime;

    @Column(name = "update_dateTime", columnDefinition = "TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6)")
    @UpdateTimestamp
    private Instant updateDateTime;

    public Long getSampleEntityId() {
        return sampleEntityId;
    }

    public void setSampleEntityId(Long sampleEntityId) {
        this.sampleEntityId = sampleEntityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WrongChildEntity getMainWrongChildEntity() {
        return mainWrongChildEntity;
    }

    public void setMainWrongChildEntity(WrongChildEntity mainWrongChildEntity) {
        this.mainWrongChildEntity = mainWrongChildEntity;
    }

    public List<WrongChildEntity> getOtherChildEntities() {
        return otherChildEntities;
    }

    public void setOtherChildEntities(List<WrongChildEntity> otherChildEntities) {
        this.otherChildEntities = otherChildEntities;
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
