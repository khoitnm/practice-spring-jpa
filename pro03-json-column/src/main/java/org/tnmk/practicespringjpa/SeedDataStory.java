package org.tnmk.practicespringjpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringjpa.pro03jsoncolumn.datafactory.SampleEntityFactory;
import org.tnmk.practicespringjpa.pro03jsoncolumn.entity.SampleEntity;
import org.tnmk.practicespringjpa.pro03jsoncolumn.story.SampleStory;

@Service
public class SeedDataStory {

    @Autowired
    private SampleStory sampleStory;

    @EventListener(ApplicationReadyEvent.class)
    public void autoStart(){
        SampleEntity sampleEntity = SampleEntityFactory.constructSampleEntityWithChildren();
        sampleStory.create(sampleEntity);
    }
}
