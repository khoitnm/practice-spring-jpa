package org.tnmk.practice.springgrpc.pro01simpleentity.sample.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SampleEntity {
    @Id
    private String id;

    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
