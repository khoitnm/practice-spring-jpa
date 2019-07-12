package org.tnmk.practicespringjpa.pro04flyway.sample.entity;

import javax.persistence.*;

@Entity
@Table(name = "sample_entity", catalog = "practice_spring_jpa_flyway_db")
public class SampleEntity {

    @Id
    // In the flyway script, we already use `AUTO_INCREMENT`, that's why we have to define `GenerationType.IDENTITY`
    // They must be compatible
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sample_entity_id")
    private Long sampleEntityId;

    private String name;

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
}
