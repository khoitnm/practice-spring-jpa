package org.tnmk.common.jpa.columnconverter.json.factory;

import org.tnmk.practicespringjpa.pro02onetomany.sample.entity.ChildEntity;
import org.tnmk.practicespringjpa.pro02onetomany.sample.entity.ParentEntity;

import java.util.List;

public class ParentFactory {
    public static ParentEntity constructParent(String name) {
        ParentEntity parentEntity = new ParentEntity();
        parentEntity.setName(name);
        return parentEntity;
    }

    public static ParentEntity constructParent() {
        return constructParent("Parent_" + System.nanoTime());
    }

    public static ParentEntity constructParentAndChildren(String parentName, int numOfChildren) {
        ParentEntity parentEntity = constructParent(parentName);
        List<ChildEntity> childEntities = ChildFactory.constructChildren(numOfChildren);
        parentEntity.setChildren(childEntities);
        return parentEntity;
    }

    public static ParentEntity constructParentAndChildren(int numOfChildren) {
        return constructParentAndChildren("Parent_"+System.nanoTime(), numOfChildren);
    }
}
