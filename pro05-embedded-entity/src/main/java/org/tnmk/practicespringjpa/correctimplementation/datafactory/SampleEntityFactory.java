package org.tnmk.practicespringjpa.correctimplementation.datafactory;

import org.tnmk.practicespringjpa.correctimplementation.entity.SampleEntity;

import java.util.Arrays;

public class SampleEntityFactory {
    public static SampleEntity constructSampleEntityWithChildren() {
        SampleEntity sampleEntity = new SampleEntity();
        sampleEntity.setName("Sample_" + System.nanoTime());
        sampleEntity.setMainChildEntity(ChildEntityFactory.constructChildEntity());
        sampleEntity.setOtherChildEntities(Arrays.asList(ChildEntityFactory.constructChildEntity(), ChildEntityFactory.constructChildEntity()));
        return sampleEntity;
    }

    public static SampleEntity constructSampleEntity() {
        SampleEntity sampleEntity = new SampleEntity();
        sampleEntity.setName("Sample_" + System.nanoTime());
        return sampleEntity;
    }
}
