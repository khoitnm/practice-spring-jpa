package org.tnmk.common.jpa.columnconverter.json

import org.springframework.beans.factory.annotation.Autowired
import org.tnmk.practicespringjpa.pro01simpletimestampfields.sample.entity.SampleEntity
import org.tnmk.practicespringjpa.pro01simpletimestampfields.sample.story.SampleStory

class SampleJdbcTemplateSpec extends BaseSpecification{



    @Autowired
    SampleStory sampleStory;

    def 'can get concat field with jdbcTemplate'(){
        given:
        SampleEntity sampleEntity = new SampleEntity(
                name: "Entity_"+System.nanoTime()
        );
        SampleEntity savedEntity = sampleStory.create(sampleEntity);

        when:
        Optional<SampleEntity> foundEntityOptional = sampleStory.findById(savedEntity.getId());

        then:
        savedEntity.getCreatedDateTime() != null
        foundEntityOptional.isPresent()
        foundEntityOptional.get().getCreatedDateTime() != null
    }
}
