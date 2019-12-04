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
    @CollectionTable(name = "person_event", joinColumns = @JoinColumn(name="person_id"))
    @Column(name = "person_event_id", nullable = false)
    private Set<PersonEvent> personEvents = new HashSet<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Set<PersonEvent> getPersonEvents() {
        return personEvents;
    }

    public void setPersonEvents(Set<PersonEvent> personEvents) {
        this.personEvents = personEvents;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
