package org.tnmk.common.jpa.columnconverter.sample.entity;

import org.tnmk.common.jpa.columnconverter.sample.entity.columnconverter.SampleEntityConverter;
import org.tnmk.common.jpa.columnconverter.sample.entity.columnconverter.SampleEntityListConverter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "sample_entity", catalog = "practice_spring_jpa")
public class SampleEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "sample_entity_id")
    private Long sampleEntityId;

    private String name;

    @Column(name = "main_child_entity", columnDefinition = "JSON")
    @Convert(converter = SampleEntityConverter.class)
    private ChildEntity mainChildEntity;

    @Column(name = "other_child_entities", columnDefinition = "JSON")
    @Convert(converter = SampleEntityListConverter.class)
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
