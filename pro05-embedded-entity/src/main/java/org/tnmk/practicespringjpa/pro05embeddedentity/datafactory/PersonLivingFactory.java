package org.tnmk.practicespringjpa.pro05embeddedentity.datafactory;

import org.tnmk.practicespringjpa.pro05embeddedentity.entity.LivingEvent;
import org.tnmk.practicespringjpa.pro05embeddedentity.entity.PersonLiving;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class PersonLivingFactory {
    public static Set<PersonLiving> constructPersonLivingList(int numOfEvents) {
        Set<PersonLiving> personEvents = new HashSet<>();
        for (int i = 0; i < numOfEvents; i++) {
            PersonLiving personEvent = constructPersonLiving();
            personEvents.add(personEvent);
        }
        return personEvents;
    }

    public static PersonLiving constructPersonLiving() {
        UUID cityId = UUID.randomUUID();
        PersonLiving personCity = new PersonLiving();
        personCity.setCityId(cityId);
        personCity.setDescription("Moving_to_city_" + cityId.toString());
        personCity.setLivingEvent(constructLivingEvent());
        return personCity;
    }

    public static LivingEvent constructLivingEvent() {
        LivingEvent livingEvent = new LivingEvent();
        livingEvent.setDescription("Moving to new city");
//        livingEvent.setStartDateTime(Instant.now());
        return livingEvent;
    }
}
