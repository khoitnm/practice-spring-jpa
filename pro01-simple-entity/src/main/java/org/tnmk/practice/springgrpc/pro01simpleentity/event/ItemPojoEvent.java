package org.tnmk.practice.springgrpc.pro01simpleentity.event;

public class ItemPojoEvent implements ItemEvent {
    private String id;
    private String name;

    public String toString(){
        return "{id: "+id+", name: "+name+"}";
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
