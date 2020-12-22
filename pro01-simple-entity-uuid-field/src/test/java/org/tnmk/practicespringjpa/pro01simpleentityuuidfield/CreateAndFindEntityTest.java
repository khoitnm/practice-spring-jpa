package org.tnmk.practicespringjpa.pro01simpleentityuuidfield;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.practicespringjpa.BaseSpringTest;
import org.tnmk.practicespringjpa.pro01simpleentityuuidfield.datafactory.PersonFactory;
import org.tnmk.practicespringjpa.pro01simpleentityuuidfield.entity.Person;
import org.tnmk.practicespringjpa.pro01simpleentityuuidfield.repository.PersonRepository;
import org.tnmk.practicespringjpa.pro01simpleentityuuidfield.story.PersonService;

import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional
public class CreateAndFindEntityTest extends BaseSpringTest {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void test_can_create_and_find_data_successfully() {
        Person constructedPerson = PersonFactory.constructPerson();
        Person savedNewPerson = personService.create(constructedPerson);
        Assert.assertNotNull(savedNewPerson.getId());

        List<Person> allPersons = personService.findAll();
        logger.info("All persons: \n" + toStringMultipleLines(allPersons));

        checkExistPerson(savedNewPerson.getId());

        checkExistPersonByAssociatedId(constructedPerson.getSomeAssociatedId());
    }

    private void checkExistPerson(UUID personId){
        logger.info("Finding person " + personId);
        Optional<Person> foundNewPersonOptional = personService.findById(personId);
        Assert.assertTrue(foundNewPersonOptional.isPresent());
        Person foundNewPerson = foundNewPersonOptional.get();
        Assert.assertNotNull(foundNewPerson.getId());
    }

    private void checkExistPersonByAssociatedId(UUID associatedId){
        List<Person> personsWithAssociatedId = personRepository.findBySomeAssociatedId(associatedId);
        logger.info("Persons with associated Id {}: \n{}", associatedId, toStringMultipleLines(personsWithAssociatedId));
        Assert.assertTrue(!personsWithAssociatedId.isEmpty());
    }

    private static String toStringMultipleLines(List<?> items) {
        String string = "[\n"+items.stream().map(item -> item.toString()).collect(Collectors.joining("\n"))+"\n]";
        return string;
    }

}
