package org.tnmk.common.jpa.columnconverter.json

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.tnmk.practicespringjpa.pro01simpleentity.sample.entity.SampleEntity
import org.tnmk.practicespringjpa.pro01simpleentity.sample.repository.SampleRepository

class SampleDeleteAllSpec extends BaseSpecification {

    private static final Logger logger = LoggerFactory.getLogger(SampleDeleteAllSpec.class);
    @Autowired
    SampleRepository sampleRepository;

    def 'delete all'() {
        given:
        List<SampleEntity> sampleEntities = createEntities(3)
        SampleEntity firstEntity = sampleEntities.get(0);
        when:
//        logger.info("Start to delete all");
//        sampleRepository.deleteAll();

        logger.info("Start to delete by name");
        sampleRepository.deleteByName(firstEntity.getName());

        then:
        noExceptionThrown();
    }

    private List<SampleEntity> createEntities(int entitiesCount) {
        List<SampleEntity> result = new ArrayList<>();
        for (int i = 0; i < entitiesCount; i++) {
            SampleEntity sampleEntity = new SampleEntity(
                    name: "SameName"
            );
            result.add(sampleRepository.save(sampleEntity));
        }
        return result;
    }
}
