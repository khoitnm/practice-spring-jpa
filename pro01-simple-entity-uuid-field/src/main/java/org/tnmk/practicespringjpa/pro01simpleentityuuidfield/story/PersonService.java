package org.tnmk.practicespringjpa.pro01simpleentityuuidfield.story;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringjpa.pro01simpleentityuuidfield.entity.Person;
import org.tnmk.practicespringjpa.pro01simpleentityuuidfield.repository.PersonRepository;

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
        person.setId(UUID.randomUUID());
        return personRepository.save(person);
    }

    public Person update(Person person) {
        if (person.getId() == null){
            throw new IllegalArgumentException("you cannot update an entity with null id.");
        }
        return personRepository.save(person);
    }

    public Optional<Person> findById(UUID personId){
        return personRepository.findById(personId);
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }
}
