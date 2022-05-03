package org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr03_eager_vs_lazy_load.pr03_00_eager;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChildWithEagerLoadService {

  private final ChildWithEagerLoadRepository childRepository;

  public Optional<ChildWithEagerLoadEntity> findById(long childId) {
    return childRepository.findById(childId);
  }

  public List<ChildWithEagerLoadEntity> findByIds(List<Long> childIds) {
    return childRepository.findAllById(childIds);
  }

  public List<ChildWithEagerLoadEntity> findByNameContaining(String name) {
    return childRepository.findByNameContaining(name);
  }
}
