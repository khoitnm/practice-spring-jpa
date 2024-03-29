package org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pro01_03_eager_vs_lazy_load.pr03_01_lazy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChildWithLazyLoadService {

  private final ChildWithLazyLoadRepository childRepository;

  public Optional<ChildWithLazyLoadEntity> findById(long childId) {
    return childRepository.findById(childId);
  }

  public List<ChildWithLazyLoadEntity> findByIds(List<Long> childIds) {
    return childRepository.findAllById(childIds);
  }

  public List<ChildWithLazyLoadEntity> findByNameContaining(String name) {
    return childRepository.findByNameContaining(name);
  }

  // This transaction annotation will make the DB Session is still open when lazy loaded is executed.
  @Transactional(readOnly = true)
  public List<ChildWithLazyLoadEntity> findByNameContaining_AndLazyLoadParentInTnx(String name) {
    List<ChildWithLazyLoadEntity> result = childRepository.findByNameContaining(name);

    // This will force parents will be lazy loaded inside service layer where we still have Session.
    // It will avoid LazyInitializationException problem when client code access parent's fields outside this method.
    //
    // Note: we cannot use parallel stream here, otherwise, we'll get exception:
    //  java.lang.NullPointerException: null
    //	at org.hibernate.engine.internal.StatefulPersistenceContext.clear(StatefulPersistenceContext.java:236)
    //	at org.hibernate.internal.SessionImpl.cleanupOnClose(SessionImpl.java:567)
    // The reason is: EntityManager concurrently is not supported
    //  https://hibernate.atlassian.net/browse/HHH-12325
    result.stream()
        .forEach(child -> child.getParentEntity().getName());
    return result;
  }

  public List<ChildWithLazyLoadEntity> findByNameContaining_AndJoinParent(String name) {
    return childRepository.findByChildNameContaining_AndJoinParent(name);
  }
}
