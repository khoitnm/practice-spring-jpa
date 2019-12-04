package org.tnmk.practicespringjpa.pro01simpleentityuuidfield.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "person")
public class Person {

    @Id
    @Column(name = "person_id", nullable = false, unique = true)
    private UUID personId;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    public UUID getPersonId() {
        return personId;
    }

    public void setPersonId(UUID personId) {
        this.personId = personId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
