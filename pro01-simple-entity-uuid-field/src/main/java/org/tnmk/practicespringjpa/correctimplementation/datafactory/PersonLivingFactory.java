package org.tnmk.practicespringjpa.correctimplementation.datafactory;

import org.tnmk.practicespringjpa.correctimplementation.entity.PersonLiving;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class PersonLivingFactory {
    public static Set<PersonLiving> constructEvents(int numOfEvents) {
        Set<PersonLiving> personEvents = new HashSet<>();
        for (int i = 0; i < numOfEvents; i++) {
            PersonLiving personEvent = constructEvent();
            personEvents.add(personEvent);
        }
        return personEvents;
    }

    public static PersonLiving constructEvent() {
        UUID cityId = UUID.randomUUID();
        PersonLiving personCity = new PersonLiving();
        personCity.setCityId(cityId);
        personCity.setDescription("Moving_to_city_" + cityId.toString());
        personCity.setStartDateTime(Instant.now());
        return personCity;
    }
}
