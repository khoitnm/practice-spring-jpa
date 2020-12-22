package org.tnmk.practicespringjpa.pro05embeddedentity.entity;

import org.tnmk.common.jpa.columnconverter.json.InstantTimeConverter;

import javax.persistence.Convert;
import java.time.Instant;

public class LivingEvent {
    private String description;
//
//    @Convert(converter = InstantTimeConverter.class)
//    private Instant startDateTime;
//
//    @Convert(converter = InstantTimeConverter.class)
//    private Instant endDateTime;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public Instant getStartDateTime() {
//        return startDateTime;
//    }
//
//    public void setStartDateTime(Instant startDateTime) {
//        this.startDateTime = startDateTime;
//    }
//
//    public Instant getEndDateTime() {
//        return endDateTime;
//    }
//
//    public void setEndDateTime(Instant endDateTime) {
//        this.endDateTime = endDateTime;
//    }
}
