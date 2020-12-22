package org.tnmk.practicespringjpa.pro05embeddedentity.story;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringjpa.pro05embeddedentity.entity.Person;
import org.tnmk.practicespringjpa.pro05embeddedentity.entity.PersonLiving;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class PersonLifeService {
    private final PersonService personService;

    @Autowired
    public PersonLifeService(PersonService personService) {
        this.personService = personService;
    }

    public Person updatePersonAndPersonLiving(String personFullName, PersonLiving personLiving) {
        Person person = personService.findByFullNameIgnoreCase(personFullName).get();
        
        person.getPersonLivings().add(personLiving);
        return personService.update(person);
    }
}
