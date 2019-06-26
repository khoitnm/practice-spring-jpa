package org.tnmk.common.jpa.columnconverter.json

import org.springframework.beans.factory.annotation.Autowired
import org.tnmk.practicespringjpa.pro01simpletimestampfields.sample.entity.SampleEntity
import org.tnmk.practicespringjpa.pro01simpletimestampfields.sample.story.SampleStory

class TimestampFieldsSpec extends BaseSpecification {


    @Autowired
    SampleStory sampleStory;

    def 'Can save and get timestamp fields'() {
        given:
        SampleEntity newEntity = new SampleEntity(
                name: "Entity_" + System.nanoTime()
        );
        SampleEntity savedNewEntity = sampleStory.create(newEntity);

        when:
        SampleEntity foundNewEntity = sampleStory.findById(savedNewEntity.getId());

        /**
         * UpdateEntity's {@link SampleEntity#createdDateTime} and {@link SampleEntity#updateDateTime} are null.
         * And they should be automatically updated correctly.
         * However, the {@link SampleEntity#createdDateTime}  will not be automatically return from the {@link SampleStory#update(SampleEntity)} (even though the {@link SampleEntity#updateDateTime} will be automatically return because DB will automatically change that value).
         * We must retrieve {@link SampleEntity} from DB again to get {@link SampleEntity#createdDateTime}.
         */
        SampleEntity updateEntity = new SampleEntity();
        updateEntity.setId(savedNewEntity.getId());
        SampleEntity savedUpdateEntity = sampleStory.update(updateEntity);

        SampleEntity foundUpdatedEntity = sampleStory.findById(savedNewEntity.getId());


        then:
        savedNewEntity.getCreatedDateTime() != null
        foundNewEntity.getCreatedDateTime() != null

        foundNewEntity.getUpdateDateTime().equals(savedNewEntity.getUpdateDateTime())

        savedUpdateEntity.getCreatedDateTime() == null //because we didn't set any value for it.
        savedUpdateEntity.getUpdateDateTime().isAfter(foundNewEntity.getUpdateDateTime())

        foundUpdatedEntity.getCreatedDateTime() != null
        foundUpdatedEntity.getCreatedDateTime().equals(savedNewEntity.getCreatedDateTime())
        foundUpdatedEntity.getUpdateDateTime().equals(savedUpdateEntity.getUpdateDateTime())
    }
}
