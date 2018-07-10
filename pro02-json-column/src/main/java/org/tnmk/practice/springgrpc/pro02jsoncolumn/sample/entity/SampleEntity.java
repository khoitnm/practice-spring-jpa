package org.tnmk.practice.springgrpc.pro02jsoncolumn.sample.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "sample_entity", catalog = "sample_db")
public class SampleEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "sample_entity_id")
    private Long sampleEntityId;

    private String name;

    private ChildEntity mainChildEntity;

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
