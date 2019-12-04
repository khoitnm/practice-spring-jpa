package org.tnmk.practicespringjpa.pro05embeddedentity.datafactory;

import org.tnmk.practicespringjpa.pro05embeddedentity.entity.Person;
import org.tnmk.practicespringjpa.pro05embeddedentity.entity.PersonLiving;

import java.util.Set;

public class PersonFactory {
    public static Person constructPersonWithEvents(int numOfEvents) {
        Person person = new Person();
        person.setFullName("Sample_" + System.nanoTime());
        Set<PersonLiving> personEvents = PersonLivingFactory.constructPersonLivingList(numOfEvents);
        person.setPersonLivings(personEvents);
        return person;
    }

    public static Person constructPerson() {
        Person person = new Person();
        person.setFullName("Sample_" + System.nanoTime());
        return person;
    }
}
