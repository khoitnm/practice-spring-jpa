package org.tnmk.common.jpa.columnconverter.json

import org.springframework.beans.factory.annotation.Autowired
import org.tnmk.common.jpa.columnconverter.json.factory.ParentFactory
import org.tnmk.practicespringjpa.pro02onetomany.sample.entity.ParentEntity
import org.tnmk.practicespringjpa.pro02onetomany.sample.story.ParentStory

class ParentStorySpec extends BaseSpecification{

    @Autowired
    ParentStory sampleStory;

    def 'can save parent'(){
        given:
        ParentEntity parentEntity = ParentFactory.constructParentAndChildren();

        when:
        sampleStory.createParentOnly(parentEntity);

        then:

    }
}
