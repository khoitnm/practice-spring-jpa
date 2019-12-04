package org.tnmk.practicespringjpa.pro01simpleentityuuidfield.datafactory;

import org.tnmk.practicespringjpa.pro01simpleentityuuidfield.entity.Person;

public class PersonFactory {

    public static Person constructPerson() {
        Person person = new Person();
        person.setFullName("Sample_" + System.nanoTime());
        return person;
    }
}
