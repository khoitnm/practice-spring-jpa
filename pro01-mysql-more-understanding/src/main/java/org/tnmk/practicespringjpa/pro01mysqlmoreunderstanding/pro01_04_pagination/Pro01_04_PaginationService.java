package org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pro01_04_pagination;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.common.SimpleEntity;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.common.SimpleRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class Pro01_04_PaginationService {
  private final SimpleRepository simpleRepository;

  public List<SimpleEntity> findByNameContaining(String name, PageRequest pageRequest) {
    log.info("Find entities by name '{}', pageRequest {}: starting...", name, pageRequest);
    List<SimpleEntity> result = simpleRepository.findByNameContaining(name, pageRequest).getContent();
    log.info("Find entities by name '{}', pageRequest {}: finish!!! Result: {}", name, pageRequest, result.size());
    return result;
  }
}
