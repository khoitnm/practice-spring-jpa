package org.tnmk.practicespringjpa.samplebusiness.story;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringjpa.samplebusiness.datafactory.SampleEntityFactory;
import org.tnmk.practicespringjpa.samplebusiness.entity.ParentEntity;

@Service
public class SeedDataStory {

    @Autowired
    private SampleStory sampleStory;

    @EventListener(ApplicationReadyEvent.class)
    public void autoStart(){
        ParentEntity parentEntity = SampleEntityFactory.constructSampleEntity();
        sampleStory.create(parentEntity);
    }
}
