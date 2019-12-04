package org.tnmk.practicespringjpa.correctimplementation.datafactory;

import org.tnmk.practicespringjpa.correctimplementation.entity.Person;
import org.tnmk.practicespringjpa.correctimplementation.entity.PersonLiving;

import java.util.Set;

public class PersonFactory {
    public static Person constructPersonWithEvents(int numOfEvents) {
        Person person = new Person();
        person.setFullName("Sample_" + System.nanoTime());
        Set<PersonLiving> personEvents = PersonLivingFactory.constructEvents(numOfEvents);
        person.setPersonLivings(personEvents);
        return person;
    }

    public static Person constructPerson() {
        Person person = new Person();
        person.setFullName("Sample_" + System.nanoTime());
        return person;
    }
}
