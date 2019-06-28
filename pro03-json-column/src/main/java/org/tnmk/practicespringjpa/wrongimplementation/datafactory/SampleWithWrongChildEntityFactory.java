package org.tnmk.practicespringjpa.wrongimplementation.datafactory;

import org.tnmk.practicespringjpa.wrongimplementation.entity.WrongChildEntity;
import org.tnmk.practicespringjpa.wrongimplementation.entity.SampleWithWrongChildEntity;

import java.util.Arrays;

public class SampleWithWrongChildEntityFactory {
    public static SampleWithWrongChildEntity constructSampleEntity() {
        SampleWithWrongChildEntity sampleWithWrongChildEntity = new SampleWithWrongChildEntity();
        sampleWithWrongChildEntity.setName("Sample_" + System.nanoTime());
        sampleWithWrongChildEntity.setMainWrongChildEntity(constructChildEntity());
        sampleWithWrongChildEntity.setOtherChildEntities(Arrays.asList(constructChildEntity(), constructChildEntity()));
        return sampleWithWrongChildEntity;
    }

    public static WrongChildEntity constructChildEntity() {
        WrongChildEntity wrongChildEntity = new WrongChildEntity();
        wrongChildEntity.setName("Child_" + System.nanoTime());
        wrongChildEntity.setDescription("Description_" + System.nanoTime());
        return wrongChildEntity;
    }
}
