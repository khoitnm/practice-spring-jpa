package org.tnmk.practicespringjpa.pro00mssql03manydata.sample;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChangeEntityNameService {
    private final SampleRepository sampleRepository;

    /**
     * This method only changes names of entities, but won't save those new names to DB.
     */
    public List<SampleEntity> changeRandomNamesForTopItems(String prefix, int updateCount) {
        List<SampleEntity> topItems =
            sampleRepository.findAll(PageRequest.of(0, updateCount)).getContent();

        for (SampleEntity sampleEntity : topItems) {
            sampleEntity.setName(prefix + "_" + System.nanoTime());
        }
        return topItems;
    }

    public List<SampleEntity> changeRandomNamesAndEntityCodesForTopItems(String prefixName, String prefixCode, int updateCount) {
        List<SampleEntity> topItems =
            sampleRepository.findAll(PageRequest.of(0, updateCount)).getContent();

        for (SampleEntity sampleEntity : topItems) {
            sampleEntity.setName(prefixName + "_" + System.nanoTime());
            sampleEntity.setEntityCode(prefixCode + "_" + System.nanoTime());
        }
        return topItems;
    }

    public List<SampleEntity> changeRandomNamesAndEntityCodesForTopItems_withSomeErrorItems(
        String prefixName, String prefixCode, int updateCount
    ) {
        List<SampleEntity> topItems =
            sampleRepository.findAll(PageRequest.of(0, updateCount)).getContent();

        for (SampleEntity sampleEntity : topItems) {
            sampleEntity.setName(prefixName + "_" + System.nanoTime());
            if (sampleEntity.getId() < 13) {
                sampleEntity.setEntityCode(prefixCode + "_" + System.nanoTime());
            } else {
                // these items will cause errors when updating.
                sampleEntity.setEntityCode("duplicated");
            }
        }
        return topItems;
    }
}
