package org.tnmk.practice.springgrpc.pro01simpleentity.event;

public interface ItemEvent {
    String getId();

    void setId(String id);

    String getName();

    void setName(String name);
}
