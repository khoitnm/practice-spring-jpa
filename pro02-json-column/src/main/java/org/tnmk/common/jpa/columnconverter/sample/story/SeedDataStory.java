package org.tnmk.common.jpa.columnconverter.sample.story;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class SeedDataStory {

    @Autowired
    private SampleStory sampleStory;

    @EventListener(ApplicationReadyEvent.class)
    public void autoStart(){
        sampleStory.createSample();
    }
}
