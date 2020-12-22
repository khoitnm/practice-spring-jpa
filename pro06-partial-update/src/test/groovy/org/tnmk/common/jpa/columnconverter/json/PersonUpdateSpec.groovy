package org.tnmk.common.jpa.columnconverter.json

import org.springframework.beans.factory.annotation.Autowired
import org.tnmk.practicespringjpa.pro06partialupdate.sample.entity.PersonEntity
import org.tnmk.practicespringjpa.pro06partialupdate.sample.model.PersonProfileUpdate
import org.tnmk.practicespringjpa.pro06partialupdate.sample.story.PersonStory

class PersonUpdateSpec extends BaseSpecification {

    @Autowired
    PersonStory sampleStory;

    def 'can partial update person profile'() {
        given:
        PersonEntity originalPerson = new PersonEntity(
                fullName: "OriginalName_" + System.nanoTime()
        );
        sampleStory.create(originalPerson);

        when:
        PersonProfileUpdate personProfileUpdate = new PersonProfileUpdate(id: originalPerson.getId(), fullName: "NewName_"+System.nanoTime());
        int updatedCount = sampleStory.updateProfile(personProfileUpdate);

        List<PersonEntity> foundUpdatedPersons = sampleStory.findPersons();
        PersonEntity foundUpdatedPerson = foundUpdatedPersons.get(0);

        then:
        updatedCount == 1
        foundUpdatedPerson.fullName == personProfileUpdate.fullName
        foundUpdatedPerson.fullName != originalPerson.fullName
        foundUpdatedPerson.updateDateTime != originalPerson.updateDateTime
    }

    def 'error when updating duplicated'() {
        given:
        PersonEntity originalPerson00 = new PersonEntity(fullName: "OriginalName_" + System.nanoTime(), email: "0@g.com");
        sampleStory.create(originalPerson00);

        PersonEntity originalPerson = new PersonEntity(fullName: "OriginalName_" + System.nanoTime(), email: "1@g.com");
        sampleStory.create(originalPerson);

        when:
        PersonProfileUpdate personProfileUpdate = new PersonProfileUpdate(
                id: originalPerson.getId(),
                fullName: "NewName_"+System.nanoTime(),
                email: originalPerson00.email);//Use the same email as originalPerson00
        int updatedCount = sampleStory.updateProfile(personProfileUpdate);

        then:
        Exception ex = thrown()
        ex != null
    }
}
