package org.tnmk.practicespringjpa.pro06partialupdate.sample.story;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

  public int updateProfile(PersonProfileUpdate personProfileUpdate) {
    try {
      int updatedCount = personRepository.updateProfile(personProfileUpdate.getId(), personProfileUpdate.getFullName(), personProfileUpdate.getEmail());
      if (updatedCount == 0) {
        throw new RecordNotFoundException("Not found any record with id " + personProfileUpdate.getId());
      }
      return updatedCount;
    } catch (DataIntegrityViolationException ex) {
      //This case can happen when we update duplicate values to a unique column.
      Throwable cause = ex.getCause();
      if (cause instanceof ConstraintViolationException) {
        ConstraintViolationException causeEx = (ConstraintViolationException) cause;
        causeEx.getConstraintName();//this is the violated unique key (not the column name)
        //TODO Try to get the detail information (table, unique key, field name), but it's tough because information was described as a string.
        // Hence it's very difficult to return a friendly error message to client apps in this case!
      }
      throw new IllegalStateException(ex.getMessage(), ex);
    } catch (Exception ex) {
      throw new IllegalStateException(ex.getMessage(), ex);
    }
  }

  public int updatePersonDetails(PersonDetailsUpdate personDetailsUpdate) {
    throw new UnsupportedOperationException("TODO");
  }

  public List<PersonEntity> findPersons() {
    return personRepository.findAll();
  }
}
