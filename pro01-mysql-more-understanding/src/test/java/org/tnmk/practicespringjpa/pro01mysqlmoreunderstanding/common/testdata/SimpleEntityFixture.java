package org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.common.testdata;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.common.SimpleEntity;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.common.SimpleRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class SimpleEntityFixture {
  public static final String ENTITY_CODE_PREFIX = "Code_";
  public static final String ENTITY_NAME_PREFIX = "Name_";
  private final SimpleRepository simpleRepository;

  public List<SimpleEntity> random(int entitiesCount) {
    return IntStream.range(0, entitiesCount).mapToObj(i -> random())
        .collect(Collectors.toList());
  }

  public SimpleEntity random() {
    SimpleEntity simpleEntity = new SimpleEntity(ENTITY_CODE_PREFIX + UUID.randomUUID(), ENTITY_NAME_PREFIX + UUID.randomUUID());
    simpleEntity = simpleRepository.save(simpleEntity);
    return simpleEntity;
  }
}
