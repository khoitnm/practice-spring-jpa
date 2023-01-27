package org.tnmk.practicespringjpa.pro00mssql03manydata.sample;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SampleBatchUpdateService {
  private final SampleRepository sampleRepository;
  private final SampleBatchUpdateRepository sampleBatchUpdateRepository;

  public void updateTopItems(int updateCount) {
    List<SampleEntity> itemsToBeUpdated =
        sampleRepository.findAll(PageRequest.of(0, updateCount)).getContent();

    for (SampleEntity sampleEntity : itemsToBeUpdated) {
      sampleEntity.setName("BatchUpdatedName_" + System.nanoTime());
    }

    sampleBatchUpdateRepository.updateEntityCodesForEntities(itemsToBeUpdated);
    log.info("itemsToBeUpdated: \n{}", itemsToBeUpdated.size());
  }
}
