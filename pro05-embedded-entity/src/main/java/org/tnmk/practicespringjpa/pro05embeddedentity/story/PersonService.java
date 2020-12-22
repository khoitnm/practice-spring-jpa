package org.tnmk.practicespringjpa.pro05embeddedentity.story;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringjpa.pro05embeddedentity.entity.Person;
import org.tnmk.practicespringjpa.pro05embeddedentity.entity.PersonLiving;
import org.tnmk.practicespringjpa.pro05embeddedentity.repository.PersonRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class PersonService {
    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person create(Person person) {
        person.setPersonId(UUID.randomUUID());
        return personRepository.save(person);
    }

    /**
     * this function will update both {@link Person} and {@link PersonLiving} (embeddable table)
     *
     * @param person
     * @return
     */
    public Person update(Person person) {
        if (person.getPersonId() == null) {
            throw new IllegalArgumentException("you cannot update an entity with null id.");
        }
        //In theory, this function will update both {@link Person} and {@link PersonLiving} (embeddable table)
        return personRepository.save(person);
    }

    public Optional<Person> findById(UUID personId) {
        return personRepository.findByPersonId(personId);
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Optional<Person> findByFullNameIgnoreCase(String fullName) {
        return personRepository.findOneByFullNameIgnoreCase(fullName);
    }
}
