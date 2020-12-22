package org.tnmk.practicespringjpa.pro06partialupdate.sample.story;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringjpa.pro06partialupdate.sample.entity.PersonEntity;
import org.tnmk.practicespringjpa.pro06partialupdate.sample.model.PersonDetailsUpdate;
import org.tnmk.practicespringjpa.pro06partialupdate.sample.model.PersonProfileUpdate;
import org.tnmk.practicespringjpa.pro06partialupdate.sample.repository.PersonRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional // Without this, the service cannot commit transaction, hence cannot create/update/delete items
@Service
public class PersonStory {
  @Autowired
  private PersonRepository personRepository;

  public PersonEntity create(PersonEntity personEntity) {
    return personRepository.save(personEntity);
  }

  public int updateProfile(PersonProfileUpdate personEntity) {
    return personRepository.updateProfile(personEntity.getId(), personEntity.getFullName());
  }

  public int updatePersonDetails(PersonDetailsUpdate personDetailsUpdate) {
    throw new UnsupportedOperationException("TODO");
  }

  public List<PersonEntity> findPersons() {
    return personRepository.findAll();
  }
}
