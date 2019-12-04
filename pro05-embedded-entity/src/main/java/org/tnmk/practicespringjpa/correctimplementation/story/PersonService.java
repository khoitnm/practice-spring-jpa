package org.tnmk.practicespringjpa.correctimplementation.story;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringjpa.correctimplementation.entity.Person;
import org.tnmk.practicespringjpa.correctimplementation.repository.PersonRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

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

    public Optional<Person> findById(UUID id){
        return personRepository.findById(id);
    }
}
