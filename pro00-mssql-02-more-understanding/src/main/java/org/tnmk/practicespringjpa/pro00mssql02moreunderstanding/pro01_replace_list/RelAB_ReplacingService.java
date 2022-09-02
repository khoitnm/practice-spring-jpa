package org.tnmk.practicespringjpa.pro00mssql02moreunderstanding.pro01_replace_list;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tnmk.practicespringjpa.pro00mssql02moreunderstanding.common.many_to_many.RelABRepository;
import org.tnmk.practicespringjpa.pro00mssql02moreunderstanding.common.many_to_many.RelAB;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RelAB_ReplacingService {
  private final RelABRepository relABRepository;

  @Transactional
  public void replaceList(int aId, List<RelAB> abRels) {
    log.info("RelAB_ReplacingService: start replaceList({}, {}):", aId, abRels);

    relABRepository.deleteByEntityAId(aId);
    relABRepository.saveAll(abRels);
    log.info("RelAB_ReplacingService: end replaceList({}, {}):", aId, abRels);
  }
}
