package org.tnmk.practicespringjpa.pro00mssql03manydata.sample;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BatchUpdateService {
  private final SampleRepository sampleRepository;

  public void updateTopItems(int updateCount) {
    List<SampleEntity> itemsToBeUpdated =
        sampleRepository.findAll(PageRequest.of(0, updateCount)).getContent();

    log.info("itemsToBeUpdated: {}", itemsToBeUpdated);
  }
}
