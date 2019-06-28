package org.tnmk.practicespringjpa.noredundantupdate.datafactory;

import org.tnmk.practicespringjpa.noredundantupdate.entity.ChildEntity;
import org.tnmk.practicespringjpa.noredundantupdate.entity.SampleEntity;

import java.util.Arrays;

public class SampleEntityFactory {
    public static SampleEntity constructSampleEntity() {
        SampleEntity sampleEntity = new SampleEntity();
        sampleEntity.setName("Sample_" + System.nanoTime());
        sampleEntity.setMainChildEntity(constructChildEntity());
        sampleEntity.setOtherChildEntities(Arrays.asList(constructChildEntity(), constructChildEntity()));
        return sampleEntity;
    }

    public static ChildEntity constructChildEntity() {
        ChildEntity childEntity = new ChildEntity();
        childEntity.setName("Child_" + System.nanoTime());
        childEntity.setDescription("Description_" + System.nanoTime());
        return childEntity;
    }
}
