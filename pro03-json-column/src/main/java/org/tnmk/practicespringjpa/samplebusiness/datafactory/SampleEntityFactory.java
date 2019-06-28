package org.tnmk.practicespringjpa.samplebusiness.datafactory;

import org.tnmk.practicespringjpa.samplebusiness.entity.ChildEntity;
import org.tnmk.practicespringjpa.samplebusiness.entity.ParentEntity;

import java.util.Arrays;

public class SampleEntityFactory {
    public static ParentEntity constructSampleEntity() {
        ParentEntity parentEntity = new ParentEntity();
        parentEntity.setName("Sample_" + System.nanoTime());
        parentEntity.setMainChildEntity(constructChildEntity());
        parentEntity.setOtherChildEntities(Arrays.asList(constructChildEntity(), constructChildEntity()));
        return parentEntity;
    }

    public static ChildEntity constructChildEntity() {
        ChildEntity childEntity = new ChildEntity();
        childEntity.setName("Child_" + System.nanoTime());
        childEntity.setDescription("Description_" + System.nanoTime());
        return childEntity;
    }
}
