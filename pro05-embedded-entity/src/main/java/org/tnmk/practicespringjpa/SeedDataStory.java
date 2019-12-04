package org.tnmk.practicespringjpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringjpa.correctimplementation.datafactory.PersonFactory;
import org.tnmk.practicespringjpa.correctimplementation.entity.Person;
import org.tnmk.practicespringjpa.correctimplementation.story.PersonService;

@Service
public class SeedDataStory {

    @Autowired
    private PersonService sampleStory;

    @EventListener(ApplicationReadyEvent.class)
    public void autoStart(){
        Person person = PersonFactory.constructPersonWithEvents(2);
        sampleStory.create(person);
    }
}
