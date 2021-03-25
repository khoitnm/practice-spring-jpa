package org.tnmk.practicespringjpa.pro07multitenant.datafactory;

import org.tnmk.practicespringjpa.pro07multitenant.entity.SampleEntity;

import java.util.Arrays;

public class SampleEntityFactory {
    public static SampleEntity constructSampleEntity() {
        SampleEntity sampleEntity = new SampleEntity();
        sampleEntity.setName("Sample_" + System.nanoTime());
        return sampleEntity;
    }
}
