package org.tnmk.practicespringjpa.correctimplementation.datafactory;

import org.tnmk.practicespringjpa.correctimplementation.entity.ChildEntity;

public class ChildEntityFactory {
    public static ChildEntity constructChildEntity() {
        ChildEntity childEntity = new ChildEntity();
        childEntity.setName("Child_" + System.nanoTime());
        childEntity.setDescription("Description_" + System.nanoTime());
        return childEntity;
    }
}
