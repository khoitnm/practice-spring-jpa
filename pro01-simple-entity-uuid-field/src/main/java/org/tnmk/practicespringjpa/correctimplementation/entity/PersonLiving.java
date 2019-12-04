package org.tnmk.practicespringjpa.correctimplementation.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.Instant;
import java.util.UUID;

/**
 * This table store the historical living of a Person in different cities.
 */
@Embeddable
public class PersonLiving {

//    @Id
//    @Column(name = "person_living_id")
//    private UUID id;
//    Note: there's no id in this table, the PK is the compound {personId, cityId}

    @Column(name = "city_id", nullable = false)
    private UUID cityId;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "start_date_time", nullable = true)
    private Instant startDateTime;

    public UUID getCityId() {
        return cityId;
    }

    public void setCityId(UUID cityId) {
        this.cityId = cityId;
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
