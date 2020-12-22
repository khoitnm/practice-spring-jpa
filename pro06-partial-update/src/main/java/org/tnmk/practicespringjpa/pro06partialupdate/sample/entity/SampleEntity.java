package org.tnmk.practicespringjpa.pro06partialupdate.sample.entity;

import javax.persistence.*;

@Entity
@Table(name = "sample_entity", catalog = "sample_db")
public class SampleEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "sample_entity_id")
    private Long sampleEntityId;

    private String name;

    @OneToOne
    @JoinColumn(name = "another_entity_id")
    private AnotherEntity anotherEntity;

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

    public AnotherEntity getAnotherEntity() {
        return anotherEntity;
    }

    public void setAnotherEntity(AnotherEntity anotherEntity) {
        this.anotherEntity = anotherEntity;
    }
}
