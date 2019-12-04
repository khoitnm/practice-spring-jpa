package org.tnmk.practicespringjpa.correctimplementation.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.Instant;
import java.util.UUID;

@Embeddable
public class PersonEvent {

    @Id
    @Column(name = "person_event_id")
    private UUID id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "start_date_time", nullable = true)
    private Instant startDateTime;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Instant startDateTime) {
        this.startDateTime = startDateTime;
    }
}
