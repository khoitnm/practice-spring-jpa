package org.tnmk.practicespringjpa.pro07multitenant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringjpa.pro07multitenant.common.security.SecurityContext;
import org.tnmk.practicespringjpa.pro07multitenant.datafactory.SampleEntityFactory;
import org.tnmk.practicespringjpa.pro07multitenant.entity.SampleEntity;
import org.tnmk.practicespringjpa.pro07multitenant.story.SampleStory;

import java.util.UUID;

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
