package org.tnmk.practice.springgrpc.pro01simpleentity.publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.tnmk.practice.springgrpc.pro01simpleentity.event.ItemEvent;

@Component
public class ItemPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public ItemPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishItem(ItemEvent item) {
        System.out.println("Publishing custom event. ");
        applicationEventPublisher.publishEvent(item);
    }

}
