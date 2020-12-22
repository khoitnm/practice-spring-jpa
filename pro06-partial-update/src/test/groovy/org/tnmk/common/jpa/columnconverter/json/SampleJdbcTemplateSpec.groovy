package org.tnmk.common.jpa.columnconverter.json

import org.springframework.beans.factory.annotation.Autowired
import org.tnmk.practicespringjpa.pro06partialupdate.sample.entity.SampleEntity
import org.tnmk.practicespringjpa.pro06partialupdate.sample.entity.SampleEntityWithUrl
import org.tnmk.practicespringjpa.pro06partialupdate.sample.story.SampleStory

class SampleJdbcTemplateSpec extends BaseSpecification{

    @Autowired
    SampleStory sampleStory;

    def 'can get concat field with jdbcTemplate'(){
        given:
        SampleEntity sampleEntity = new SampleEntity(
                name: "Entity_"+System.nanoTime()
        );
        sampleStory.create(sampleEntity);

        when:
        List<SampleEntityWithUrl> sampleEntityWithUrlList = sampleStory.findSampleEntitiesWithUrl();

        then:
        !sampleEntityWithUrlList.isEmpty()
    }
}
