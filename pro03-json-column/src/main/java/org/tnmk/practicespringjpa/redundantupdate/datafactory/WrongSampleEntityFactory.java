package org.tnmk.practicespringjpa.redundantupdate.datafactory;

import org.tnmk.practicespringjpa.redundantupdate.entity.WrongChildEntity;
import org.tnmk.practicespringjpa.redundantupdate.entity.WrongSampleEntity;

import java.util.Arrays;

public class WrongSampleEntityFactory {
    public static WrongSampleEntity constructSampleEntity() {
        WrongSampleEntity wrongSampleEntity = new WrongSampleEntity();
        wrongSampleEntity.setName("Sample_" + System.nanoTime());
        wrongSampleEntity.setMainWrongChildEntity(constructChildEntity());
        wrongSampleEntity.setOtherChildEntities(Arrays.asList(constructChildEntity(), constructChildEntity()));
        return wrongSampleEntity;
    }

    public static WrongChildEntity constructChildEntity() {
        WrongChildEntity wrongChildEntity = new WrongChildEntity();
        wrongChildEntity.setName("Child_" + System.nanoTime());
        wrongChildEntity.setDescription("Description_" + System.nanoTime());
        return wrongChildEntity;
    }
}
