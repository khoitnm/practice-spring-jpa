package org.tnmk.practice.springgrpc.pro01simpleentity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.tnmk.practice.springgrpc.pro01simpleentity.event.ItemPojoEvent;
import org.tnmk.practice.springgrpc.pro01simpleentity.event.ItemEvent;
import org.tnmk.practice.springgrpc.pro01simpleentity.publisher.ItemPublisher;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemPublisherTest {

    @Autowired
    private ItemPublisher itemPublisher;


    @Test
    public void testPublish(){
        ItemEvent item = new ItemPojoEvent();
        item.setId("1");
        item.setName("my name");
        itemPublisher.publishItem(item);
    }
}
