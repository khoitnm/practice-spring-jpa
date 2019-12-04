package org.tnmk.practicespringjpa.correctimplementation.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "person")
public class Person {

    @Id
    @Column(name = "person_id", nullable = false, unique = true)
    private UUID id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "person_living", joinColumns = @JoinColumn(name="person_id"))
    @Column(name = "city_id", nullable = false)
    private Set<PersonLiving> personLivings = new HashSet<>();

    @Override
    public String toString() {
        return "Person{" +
            "id=" + id +
            ", fullName='" + fullName + '\'' +
            ", personLivings=" + personLivings +
            '}';
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Set<PersonLiving> getPersonLivings() {
        return personLivings;
    }

    public void setPersonLivings(Set<PersonLiving> personEvents) {
        this.personLivings = personEvents;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
