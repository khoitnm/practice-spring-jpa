package org.tnmk.practicespringjpa.correctimplementation.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.tnmk.practicespringjpa.correctimplementation.entity.columnconverter.ChildEntityConverter;
import org.tnmk.practicespringjpa.correctimplementation.entity.columnconverter.ChildEntityListConverter;
import org.tnmk.practicespringjpa.correctimplementation.entity.columnconverter.ChildWithoutMapComparisionEntityConverter;
import org.tnmk.practicespringjpa.correctimplementation.entity.columnconverter.WrongChildEntityConverter;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

/**
 * NOTE that we can have 2 entities classes associated to the same table.
 */
//TODO SQL statement for update won't include unchanged fields. Split this demo to another module?
// https://stackoverflow.com/questions/3404630/hibernate-dynamic-update-dynamic-insert-performance-effects?noredirect=1&lq=1
// https://stackoverflow.com/questions/41633250/how-dynamic-update-true-works-internally-in-hibernate
// Note: when updating a previously detached object. For that to work the record first needs to be fetched from the db, as a detached object isn't in the session cache. Hence dynamic update in this case requires an extra round trip to perform the initial fetch.
// So, we shouldn't just use it by default. Please carefully consider before using it.
@DynamicUpdate

@Entity
@Table(name = "sample_entity"
        /**
         * This name must match with DB name (in application.yml, docker.yml, and testing EmbeddedDBStarter)
        */
        , catalog = "practice_spring_jpa_db"
)
public class SampleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "sample_entity_id")
    private Long sampleEntityId;

    private String name;

    @Column(name = "main_child_entity", columnDefinition = "JSON")
    @Convert(converter = ChildEntityConverter.class)
    private ChildEntity mainChildEntity;

    @Column(name = "wrong_child_entity", columnDefinition = "JSON")
    @Convert(converter = WrongChildEntityConverter.class)
    private WrongChildEntity wrongChildEntity;

    @Column(name = "child_without_map_comparision_entity", columnDefinition = "JSON")
    @Convert(converter = ChildWithoutMapComparisionEntityConverter.class)
    private ChildWithoutMapComparisionEntity childWithoutMapComparisionEntity;

    @Column(name = "other_child_entities", columnDefinition = "JSON")
    @Convert(converter = ChildEntityListConverter.class)
    private List<ChildEntity> otherChildEntities;

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

    public ChildEntity getMainChildEntity() {
        return mainChildEntity;
    }

    public void setMainChildEntity(ChildEntity mainChildEntity) {
        this.mainChildEntity = mainChildEntity;
    }

    public List<ChildEntity> getOtherChildEntities() {
        return otherChildEntities;
    }

    public void setOtherChildEntities(List<ChildEntity> otherChildEntities) {
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

    public WrongChildEntity getWrongChildEntity() {
        return wrongChildEntity;
    }

    public void setWrongChildEntity(WrongChildEntity wrongChildEntity) {
        this.wrongChildEntity = wrongChildEntity;
    }

    public ChildWithoutMapComparisionEntity getChildWithoutMapComparisionEntity() {
        return childWithoutMapComparisionEntity;
    }

    public void setChildWithoutMapComparisionEntity(ChildWithoutMapComparisionEntity childWithoutMapComparisionEntity) {
        this.childWithoutMapComparisionEntity = childWithoutMapComparisionEntity;
    }
}
