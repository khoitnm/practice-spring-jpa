package org.tnmk.common.jpa.columnconverter.json.factory

import org.tnmk.practicespringjpa.pro02onetomany.sample.entity.ChildEntity
import org.tnmk.practicespringjpa.pro02onetomany.sample.entity.ParentEntity

class ChildFactory {
    public static ChildEntity constructChild(){
        return constructChild(System.nanoTime());
    }

    public static List<ChildEntity> constructChildren(int numOfChildren){
        List<ChildEntity> childEntities = new ArrayList<>();
        for (int i = 0; i < numOfChildren; i++) {
            ChildEntity childEntity = constructChildren(i);
            childEntities.add(childEntity);
        }
        return childEntities;
    }

    public static ChildEntity constructChild(int index){
        return new ChildEntity(name: "Child_"+index);
    }
}
