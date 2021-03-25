package org.tnmk.practicespringjpa.pro07multitenant.correctimplementation.datafactory;

import org.tnmk.practicespringjpa.pro07multitenant.correctimplementation.entity.ChildEntity;

public class ChildEntityFactory {
    public static ChildEntity constructChildEntity() {
        ChildEntity childEntity = new ChildEntity();
        childEntity.setName("Child_" + System.nanoTime());
        childEntity.setDescription("Description_" + System.nanoTime());
        return childEntity;
    }
}
