package org.tnmk.practicespringjpa.pro00mssql02moreunderstanding.pro01_replace_list;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.practicespringjpa.pro00mssql02moreunderstanding.common.many_to_many.RelAB;
import org.tnmk.practicespringjpa.pro00mssql02moreunderstanding.common.many_to_many.RelABFactory;
import org.tnmk.practicespringjpa.pro00mssql02moreunderstanding.common.many_to_many.RelABFixture;
import org.tnmk.practicespringjpa.pro00mssql02moreunderstanding.common.many_to_many.RelABRepository;
import org.tnmk.practicespringjpa.pro00mssql02moreunderstanding.testinfra.BaseSpringTest;

import java.util.List;

@Slf4j
public class Rel_ABReplacing_ServiceTest extends BaseSpringTest {
  @Autowired
  private RelABFixture relABFixture;

  @Autowired
  private RelABRepository relABRepository;

  @Autowired
  private RelAB_ReplacingService rel_ABReplacing_service;

  @Test
  public void test() {
    // given
    int aId = 1;
    List<RelAB> existingRels = relABFixture.insertABRels(aId, 5);

    int numOfNewRels = 3;
    List<RelAB> newRels = RelABFactory.randomABRels(aId, numOfNewRels);

    // when
    rel_ABReplacing_service.replaceList(aId, newRels);

    // then
    List<RelAB> actualRelABsInDB = relABRepository.findByEntityAId(aId);
    Assertions.assertEquals(actualRelABsInDB.size(), numOfNewRels);
  }

}
