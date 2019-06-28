package org.tnmk.practicespringjpa.redundantupdate.datafactory;

import org.tnmk.practicespringjpa.redundantupdate.entity.WrongChildEntity;
import org.tnmk.practicespringjpa.redundantupdate.entity.SampleWithWrongChildEntity;

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
