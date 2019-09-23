package org.tnmk.common.jpa.columnconverter.json.factory;

import org.tnmk.practicespringjpa.pro02onetomany.sample.entity.ChildEntity;
import org.tnmk.practicespringjpa.pro02onetomany.sample.entity.ParentEntity;

import java.util.List;

public class ParentFactory {

    public static ParentEntity constructParent() {
        ParentEntity parentEntity = new ParentEntity();
        parentEntity.setName("Parent_" + System.nanoTime());
        return parentEntity;
    }

    public static ParentEntity constructParentAndChildren(int numOfChildren) {
        ParentEntity parentEntity = constructParent();
        List<ChildEntity> childEntities = ChildFactory.constructChildren(numOfChildren);
        parentEntity.setChildren(childEntities);
        return parentEntity;
    }
}
