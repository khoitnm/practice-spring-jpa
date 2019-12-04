package org.tnmk.practicespringjpa.correctimplementation.datafactory;

import org.tnmk.practicespringjpa.correctimplementation.entity.ChildWithoutMapComparisionEntity;

import java.util.HashMap;

public class ChildWithoutMapComparisionEntityFactory {

    public static ChildWithoutMapComparisionEntity constructChildEntity() {
        ChildWithoutMapComparisionEntity entity = new ChildWithoutMapComparisionEntity();
        entity.setName("Child_" + System.nanoTime());
        entity.setDescription("Description_" + System.nanoTime());
        entity.setCharacteristics(new HashMap<>());
        return entity;
    }
}
