package org.tnmk.practicespringjpa.pro03jsoncolumn.datafactory;

import org.tnmk.practicespringjpa.pro03jsoncolumn.entity.ChildEntity;

public class ChildEntityFactory {
    public static ChildEntity constructChildEntity() {
        ChildEntity childEntity = new ChildEntity();
        childEntity.setName("Child_" + System.nanoTime());
        childEntity.setDescription("Description_" + System.nanoTime());
        return childEntity;
    }
}
