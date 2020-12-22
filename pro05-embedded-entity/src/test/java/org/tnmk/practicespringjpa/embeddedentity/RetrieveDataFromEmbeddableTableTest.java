package org.tnmk.practicespringjpa.embeddedentity;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.practicespringjpa.BaseSpringTest;
import org.tnmk.practicespringjpa.pro05embeddedentity.datafactory.PersonFactory;
import org.tnmk.practicespringjpa.pro05embeddedentity.datafactory.PersonLivingFactory;
import org.tnmk.practicespringjpa.pro05embeddedentity.entity.Person;
import org.tnmk.practicespringjpa.pro05embeddedentity.entity.PersonLiving;
import org.tnmk.practicespringjpa.pro05embeddedentity.repository.PersonRepository;
import org.tnmk.practicespringjpa.pro05embeddedentity.story.PersonService;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RetrieveDataFromEmbeddableTableTest extends BaseSpringTest {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonRepository personRepository;


    @Test
    public void test_canSaveEmbeddableTableBySavingMainTable() {
        Person constructedPerson = PersonFactory.constructPersonWithEvents(2);
        Person savedNewPerson = personService.create(constructedPerson);

        PersonLiving savedNewPersonLiving = savedNewPerson.getPersonLivings().iterator().next();
        savedNewPersonLiving.setDescription("Updated Event_"+System.nanoTime());
        Person savedUpdatePerson = personService.update(savedNewPerson);

        Person foundNewPerson = personRepository.findOneByFullNameIgnoreCase(savedUpdatePerson.getFullName()).get();
        assertPersonLivingsHasFullData(foundNewPerson.getPersonLivings());
        Assert.assertEquals(foundNewPerson.getPersonLivings().iterator().next().getDescription(), savedNewPersonLiving.getDescription());
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
