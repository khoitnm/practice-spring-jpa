package org.tnmk.common.jpa.columnconverter.json


import org.springframework.beans.factory.annotation.Autowired
import org.tnmk.practicespringjpa.pro02simpleddlgenerator.sample.entity.SampleEntity
import org.tnmk.practicespringjpa.pro02simpleddlgenerator.sample.entity.SampleEntityWithUrl
import org.tnmk.practicespringjpa.pro02simpleddlgenerator.sample.repository.SampleRepository
import org.tnmk.practicespringjpa.pro02simpleddlgenerator.sample.story.SampleStory

class SampleJdbcTemplateSpec extends BaseSpecification{

    @Autowired
    SampleRepository sampleRepository;

    @Autowired
    SampleStory sampleStory;

    def 'can get concat field with jdbcTemplate'(){
        given:
        SampleEntity sampleEntity = new SampleEntity(
                name: "Entity_"+System.nanoTime()
        );
        sampleRepository.save(sampleEntity);

        when:
        List<SampleEntityWithUrl> sampleEntityWithUrlList = sampleStory.findSampleEntitiesWithUrl();

        then:
        !sampleEntityWithUrlList.isEmpty()
    }
}
