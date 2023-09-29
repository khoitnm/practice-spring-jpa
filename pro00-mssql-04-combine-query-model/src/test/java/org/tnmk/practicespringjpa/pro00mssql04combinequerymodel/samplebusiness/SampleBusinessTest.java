package org.tnmk.practicespringjpa.pro00mssql04combinequerymodel.samplebusiness;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.practicespringjpa.pro00mssql04combinequerymodel.datafactory.SampleEntityFactory;
import org.tnmk.practicespringjpa.pro00mssql04combinequerymodel.entity.CombinedModel;
import org.tnmk.practicespringjpa.pro00mssql04combinequerymodel.entity.CombinedModel_Projection;
import org.tnmk.practicespringjpa.pro00mssql04combinequerymodel.entity.ParentEntity;
import org.tnmk.practicespringjpa.pro00mssql04combinequerymodel.entity.SampleEntity;
import org.tnmk.practicespringjpa.pro00mssql04combinequerymodel.repository.ParentRepository;
import org.tnmk.practicespringjpa.pro00mssql04combinequerymodel.repository.SampleRepository;
import org.tnmk.practicespringjpa.pro00mssql04combinequerymodel.testinfra.BaseSpringTest;

import java.util.List;

@Slf4j
public class SampleBusinessTest extends BaseSpringTest {
    @Autowired
    private SampleRepository sampleRepository;
    @Autowired
    private ParentRepository parentRepository;

    private SampleEntity initSampleDataWithParent() {
        ParentEntity parentEntity = new ParentEntity();
        parentEntity.setName("parent");
        parentRepository.save(parentEntity);

        SampleEntity sampleEntity = SampleEntityFactory.constructSampleEntity(parentEntity);
        SampleEntity savedSampleEntity = sampleRepository.save(sampleEntity);
        return savedSampleEntity;
    }

    @Test
    public void test_getCombinedModels_successfully() {
        initSampleDataWithParent();

        List<CombinedModel> combinedModels = sampleRepository.getCombinedModels();

        Assertions.assertThat(combinedModels).isNotEmpty();
        log.info("CombinedModels: " + combinedModels);
        for (CombinedModel combinedModel : combinedModels) {
            Assertions.assertThat(combinedModel.getId()).isNotNull();
            Assertions.assertThat(combinedModel.getName()).isNotBlank();
            Assertions.assertThat(combinedModel.getParentName()).isNotBlank();
            Assertions.assertThat(combinedModel.getParentId()).isNotNull();
        }
    }

    /**
     * @see SampleRepository#getCombinedModelsUseProjection() for more details about why it is disabled!!!
     */
    @Test
    @Ignore
    public void test_getCombinedModelsUsingProjection_successfully() {
        initSampleDataWithParent();

        List<CombinedModel_Projection> combinedModels = sampleRepository.getCombinedModelsUseProjection();

        Assertions.assertThat(combinedModels).isNotEmpty();
        log.info("CombinedModels: " + combinedModels);
        for (CombinedModel_Projection combinedModel : combinedModels) {
            Assertions.assertThat(combinedModel.getId()).isNotNull();
            Assertions.assertThat(combinedModel.getName()).isNotBlank();
            Assertions.assertThat(combinedModel.getParentName()).isNotBlank();
            Assertions.assertThat(combinedModel.getParentId()).isNotNull();
        }
    }
}
