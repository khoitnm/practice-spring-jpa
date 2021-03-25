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
import org.tnmk.practicespringjpa.pro05embeddedentity.story.PersonLifeService;
import org.tnmk.practicespringjpa.pro05embeddedentity.story.PersonService;

import java.lang.invoke.MethodHandles;
import java.util.Set;

public class PersonLifeTest extends BaseSpringTest {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private PersonLifeService personLifeService;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonService personService;

    @Test
    public void test_canSaveEmbeddableTableFirstBeforeSavingMainTable() {
        Person constructedPerson = PersonFactory.constructPerson();
        PersonLiving personLiving = PersonLivingFactory.constructPersonLiving();

        Person createdPerson = personService.create(constructedPerson);

        Person savedUpdatePerson = personLifeService.updatePersonAndPersonLiving(createdPerson.getFullName(), personLiving);

        Person foundNewPerson = personRepository.findOneByFullNameIgnoreCase(savedUpdatePerson.getFullName()).get();
        PersonLiving foundPersonLiving = foundNewPerson.getPersonLivings().iterator().next();
        assertPersonLivingsHasFullData(foundNewPerson.getPersonLivings());

        Assert.assertEquals(foundPersonLiving.getCityId(), personLiving.getCityId());
        Assert.assertEquals(foundPersonLiving.getDescription(), personLiving.getDescription());
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
