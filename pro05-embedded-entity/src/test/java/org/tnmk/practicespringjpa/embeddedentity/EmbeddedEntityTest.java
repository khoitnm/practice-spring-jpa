package org.tnmk.practicespringjpa.embeddedentity;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.practicespringjpa.BaseSpringTest;
import org.tnmk.practicespringjpa.pro05embeddedentity.datafactory.PersonFactory;
import org.tnmk.practicespringjpa.pro05embeddedentity.entity.Person;
import org.tnmk.practicespringjpa.pro05embeddedentity.entity.PersonLiving;
import org.tnmk.practicespringjpa.pro05embeddedentity.story.PersonService;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EmbeddedEntityTest extends BaseSpringTest {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private PersonService personService;

    @Test
    public void test_embeddedEntityHasData() {
        Person constructedPerson = PersonFactory.constructPersonWithEvents(2);
        Person savedNewPerson = personService.create(constructedPerson);
        Assert.assertNotNull(savedNewPerson.getPersonId());
        assertPersonLivingsHasFullData(savedNewPerson.getPersonLivings());

        List<Person> allPersons = personService.findAll();
        String allPersonsToString = allPersons.stream().map(person -> person.toString()).collect(Collectors.joining("\n"));
        logger.info("All persons: \n" + allPersonsToString);

        Person foundNewPerson = personService.findById(savedNewPerson.getPersonId()).get();
        assertPersonLivingsHasFullData(foundNewPerson.getPersonLivings());
    }

    private void assertPersonLivingsHasFullData(Set<PersonLiving> personEvents) {
        personEvents.forEach(
            personEvent -> {
                Assert.assertNotNull(personEvent.getCityId());
                Assert.assertNotNull(personEvent.getDescription());
                Assert.assertNotNull(personEvent.getLivingEvent());
                Assert.assertNotNull(personEvent.getLivingEvent().getDescription());
//                Assert.assertNotNull(personEvent.getLivingEvent().getStartDateTime());
//                Assert.assertNotNull(personEvent.getLivingEvent().getEndDateTime());
            }
        );
    }

}
