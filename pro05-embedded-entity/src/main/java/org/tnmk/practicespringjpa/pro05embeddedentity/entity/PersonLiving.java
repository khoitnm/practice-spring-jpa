package org.tnmk.practicespringjpa.pro05embeddedentity.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
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

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
//        if (this == o) return true;
//
//        if (o == null || getClass() != o.getClass()) return false;
//
//        PersonLiving that = (PersonLiving) o;
//
//        return new EqualsBuilder()
//            .append(cityId, that.cityId)
//            .append(description, that.description)
//            .append(livingEvent, that.livingEvent)
//            .isEquals();
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
//        return new HashCodeBuilder(17, 37)
//            .append(cityId)
//            .append(description)
//            .append(livingEvent)
//            .toHashCode();
    }

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
