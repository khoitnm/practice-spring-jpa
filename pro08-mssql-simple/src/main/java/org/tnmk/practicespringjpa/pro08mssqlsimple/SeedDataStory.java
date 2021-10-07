package org.tnmk.practicespringjpa.pro08mssqlsimple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringjpa.pro08mssqlsimple.datafactory.SampleEntityFactory;
import org.tnmk.practicespringjpa.pro08mssqlsimple.entity.SampleEntity;
import org.tnmk.practicespringjpa.pro08mssqlsimple.story.SampleStory;

@Service
public class SeedDataStory {

    @Autowired
    private SampleStory sampleStory;

    @EventListener(ApplicationReadyEvent.class)
    public void autoStart(){
        SampleEntity sampleEntity = SampleEntityFactory.constructSampleEntity();
        sampleStory.create(sampleEntity);
    }
}
