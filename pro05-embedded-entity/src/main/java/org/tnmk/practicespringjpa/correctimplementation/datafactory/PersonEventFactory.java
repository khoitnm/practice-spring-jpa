package org.tnmk.practicespringjpa.correctimplementation.datafactory;

import org.tnmk.practicespringjpa.correctimplementation.entity.PersonEvent;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

public class PersonEventFactory {
    public static Set<PersonEvent> constructEvents(int numOfEvents) {
        Set<PersonEvent> personEvents = new HashSet<>();
        for (int i = 0; i < numOfEvents; i++) {
            PersonEvent personEvent = constructEvent();
            personEvents.add(personEvent);
        }
        return personEvents;
    }

    public static PersonEvent constructEvent() {
        PersonEvent personEvent = new PersonEvent();
        personEvent.setDescription("Moving_" + System.nanoTime());
        personEvent.setStartDateTime(Instant.now());
        return personEvent;
    }
}
