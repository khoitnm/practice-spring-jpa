package org.tnmk.practicespringjpa.embeddedentity;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.practicespringjpa.BaseSpringTest;
import org.tnmk.practicespringjpa.pro01simpleentityuuidfield.datafactory.PersonFactory;
import org.tnmk.practicespringjpa.pro01simpleentityuuidfield.entity.Person;
import org.tnmk.practicespringjpa.pro01simpleentityuuidfield.entity.PersonLiving;
import org.tnmk.practicespringjpa.pro01simpleentityuuidfield.story.PersonService;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;
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
        assertPersonEventsHasFullData(savedNewPerson.getPersonLivings());

        List<Person> allPersons = personService.findAll();
        String allPersonsToString = allPersons.stream().map(person -> person.toString()).collect(Collectors.joining("\n"));
        logger.info("All persons: \n"+ allPersonsToString);

        logger.info("Finding person "+savedNewPerson.getPersonId());
        Optional<Person> foundNewPersonOptional = personService.findById(savedNewPerson.getPersonId());
        Assert.assertTrue(foundNewPersonOptional.isPresent());

        Person foundNewPerson = foundNewPersonOptional.get();
        assertPersonEventsHasFullData(foundNewPerson.getPersonLivings());
    }

    private void assertPersonEventsHasFullData(Set<PersonLiving> personEvents) {
        personEvents.forEach(
            personEvent -> {
                Assert.assertNotNull(personEvent.getCityId());
                Assert.assertNotNull(personEvent.getDescription());
                Assert.assertNotNull(personEvent.getStartDateTime());
            }
        );
    }

}
