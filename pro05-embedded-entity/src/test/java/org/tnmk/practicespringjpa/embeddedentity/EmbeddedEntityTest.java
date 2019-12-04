package org.tnmk.practicespringjpa.embeddedentity;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.practicespringjpa.BaseSpringTest;
import org.tnmk.practicespringjpa.correctimplementation.datafactory.PersonFactory;
import org.tnmk.practicespringjpa.correctimplementation.entity.Person;
import org.tnmk.practicespringjpa.correctimplementation.entity.PersonEvent;
import org.tnmk.practicespringjpa.correctimplementation.story.PersonService;

import java.util.Set;

public class EmbeddedEntityTest extends BaseSpringTest {
    @Autowired
    private PersonService sampleStory;

    @Test
    public void test_embeddedEntityHasData() {
        Person constructedPerson = PersonFactory.constructPersonWithEvents(2);
        Person savedNewPerson = sampleStory.create(constructedPerson);
        assertPersonEventsHasFullData(savedNewPerson.getPersonEvents());

        Person foundNewPerson = sampleStory.findById(savedNewPerson.getId()).get();
        assertPersonEventsHasFullData(foundNewPerson.getPersonEvents());
    }
    private void assertPersonEventsHasFullData(Set<PersonEvent> personEvents){
        personEvents.forEach(
            personEvent -> {
                Assert.assertNotNull(personEvent.getId());
                Assert.assertNotNull(personEvent.getDescription());
                Assert.assertNotNull(personEvent.getStartDateTime());
            }
        );
    }

}
