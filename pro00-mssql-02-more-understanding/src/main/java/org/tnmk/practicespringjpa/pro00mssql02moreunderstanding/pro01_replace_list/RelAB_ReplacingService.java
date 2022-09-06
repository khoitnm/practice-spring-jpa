package org.tnmk.practicespringjpa.pro00mssql02moreunderstanding.pro01_replace_list;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.tnmk.practicespringjpa.pro00mssql02moreunderstanding.common.many_to_many.RelAB;
import org.tnmk.practicespringjpa.pro00mssql02moreunderstanding.common.many_to_many.RelABJdbcRepository;
import org.tnmk.practicespringjpa.pro00mssql02moreunderstanding.common.many_to_many.RelABRepository;

import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RelAB_ReplacingService {
  private final RelABRepository relABRepository;
  private final RelABJdbcRepository relABJdbcRepository;
  private final EntityManager entityManager;

  @Transactional
  public void replaceList_Deadlock_WhenRunInParallel(int aId, List<RelAB> abRels) {
    log.info("RelAB_ReplacingService: start replaceList({}, {}):", aId, abRels);

    relABRepository.deleteByEntityAId(aId);

    /**
     * This method will cause deadlock when being executed in many threads
     * because it first, will have to execute many 'select' statement, one-by-one for each item.
     * and then executing 'insert' for each item.
     * <p/>
     * Executing `select` and `insert` like that will cause deadlock in multi-threads.
     * Please see {@link #replaceList_NoDeadlock_WhenRunInParallel(int, List)} to see how to avoid it.
     */
    relABRepository.saveAll(abRels);
    log.info("RelAB_ReplacingService: end replaceList({}, {}):", aId, abRels);
  }

  @Transactional(isolation = Isolation.READ_UNCOMMITTED)
  public void replaceList_NoDeadlock_WhenRunInParallel(int aId, List<RelAB> abRels) {
    log.info("RelAB_ReplacingService: start replaceList({}, {}):", aId, abRels);

    relABRepository.deleteByEntityAId(aId);
    relABJdbcRepository.insertInBatch(abRels);
    log.info("RelAB_ReplacingService: end replaceList({}, {}):", aId, abRels);
  }
}
