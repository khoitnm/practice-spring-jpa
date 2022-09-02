package org.tnmk.practicespringjpa.pro00mssql02moreunderstanding.common.many_to_many;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RelABFixture {
  private final RelABRepository repository;

  @Transactional
  public List<RelAB> insertABRels(int aId, int numberOfRelationships) {
    log.info("RelABFixture: insertABRels({}, {}): start... ", aId, numberOfRelationships);
    List<RelAB> relAB_s = RelABFactory.randomABRels(aId, numberOfRelationships);
    relAB_s = repository.saveAll(relAB_s);

    log.info("RelABFixture: insertABRels({}, {}): ended! ", aId, numberOfRelationships);
    return relAB_s;
  }
}
