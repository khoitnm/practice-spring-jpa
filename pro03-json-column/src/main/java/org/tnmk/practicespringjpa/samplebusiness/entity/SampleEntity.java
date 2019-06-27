package org.tnmk.practicespringjpa.samplebusiness.entity;

import org.hibernate.annotations.DynamicUpdate;
import org.tnmk.practicespringjpa.samplebusiness.entity.columnconverter.ChildEntityConverter;
import org.tnmk.practicespringjpa.samplebusiness.entity.columnconverter.ChildEntityListConverter;

import javax.persistence.*;
import java.util.List;

@DynamicUpdate//TODO SQL statement for update won't include unchanged fields. Split this demo to another module?
@Entity
@Table(name = "sample_entity"
    /**
     * This name must match with DB name (in application.yml, docker.yml, and testing EmbeddedDBStarter)
     */
    , catalog = "practice_spring_jpa_db"
)
public class SampleEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "sample_entity_id")
    private Long sampleEntityId;

    private String name;

    @Column(name = "main_child_entity", columnDefinition = "JSON")
    @Convert(converter = ChildEntityConverter.class)
    private ChildEntity mainChildEntity;

    @Column(name = "other_child_entities", columnDefinition = "JSON")
    @Convert(converter = ChildEntityListConverter.class)
    private List<ChildEntity> otherChildEntities;

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
}
