package org.tnmk.practicespringjpa.pro05embeddedentity.entity;

import org.tnmk.practicespringjpa.pro05embeddedentity.entity.columnconverter.LivingEventConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
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

    @Column(name = "city_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID cityId;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "living_event", columnDefinition = "JSON")
    @Convert(converter = LivingEventConverter.class)
    private LivingEvent livingEvent;

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

    public LivingEvent getLivingEvent() {
        return livingEvent;
    }

    public void setLivingEvent(LivingEvent livingEvent) {
        this.livingEvent = livingEvent;
    }
}
