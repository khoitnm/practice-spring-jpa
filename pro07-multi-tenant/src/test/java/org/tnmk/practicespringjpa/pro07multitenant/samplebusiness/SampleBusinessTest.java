package org.tnmk.practicespringjpa.pro07multitenant.samplebusiness;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.practicespringjpa.pro07multitenant.entity.SampleEntity;
import org.tnmk.practicespringjpa.pro07multitenant.story.SampleStory;
import org.tnmk.practicespringjpa.pro07multitenant.testinfra.BaseSpringTest;

import java.util.Optional;

public class SampleBusinessTest extends BaseSpringTest {
    @Autowired
    private SampleStory sampleStory;

    @Test
    public void test() {
        // GIVEN
        String tenantId = "1";

        SampleEntity sampleEntity = new SampleEntity();
        sampleEntity.setName("Sample description");

        SampleEntity savedSampleEntity = sampleStory.createEntity(tenantId, sampleEntity);

        // WHEN 01
        Optional<SampleEntity> result01 = sampleStory.findById(tenantId, savedSampleEntity.getId());
        // THEN 01
        Assert.assertTrue(result01.isPresent());

        // WHEN 02
        String tenantId2 = "2";
        Optional<SampleEntity> result02 = sampleStory.findById(tenantId2, savedSampleEntity.getId());
        // THEN 02
        Assert.assertTrue(result02.isEmpty());

    }
}
