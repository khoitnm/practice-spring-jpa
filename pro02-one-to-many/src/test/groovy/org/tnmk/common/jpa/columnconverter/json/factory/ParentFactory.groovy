package org.tnmk.common.jpa.columnconverter.json.factory

import org.tnmk.practicespringjpa.pro02onetomany.sample.entity.ChildEntity
import org.tnmk.practicespringjpa.pro02onetomany.sample.entity.ParentEntity

class ParentFactory {
    public static ParentEntity constructParent() {
        return new ParentEntity(name: "Parent_" + System.nanoTime());
    }

    public static ParentEntity constructParentAndChildren() {
        ParentEntity parentEntity = new ParentEntity(name: "Parent_" + System.nanoTime());
        List<ChildEntity> childEntities = ChildFactory.constructChildren(3);
        parentEntity.setChildren(childEntities);
        return parentEntity;
    }
}
