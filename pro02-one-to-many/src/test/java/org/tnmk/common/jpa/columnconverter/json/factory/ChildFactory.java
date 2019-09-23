package org.tnmk.common.jpa.columnconverter.json.factory;

import org.tnmk.practicespringjpa.pro02onetomany.sample.entity.ChildEntity;

import java.util.ArrayList;
import java.util.List;

class ChildFactory {
    public static ChildEntity constructChild() {
        return constructChild(System.nanoTime());
    }

    public static List<ChildEntity> constructChildren(int numOfChildren) {
        List<ChildEntity> childEntities = new ArrayList<>();
        for (int i = 0; i < numOfChildren; i++) {
            ChildEntity childEntity = constructChild(i);
            childEntities.add(childEntity);
        }
        return childEntities;
    }

    public static ChildEntity constructChild(long index) {
        ChildEntity childEntity = new ChildEntity();
        childEntity.setName("Child_" + index);
        return childEntity;
    }
}
