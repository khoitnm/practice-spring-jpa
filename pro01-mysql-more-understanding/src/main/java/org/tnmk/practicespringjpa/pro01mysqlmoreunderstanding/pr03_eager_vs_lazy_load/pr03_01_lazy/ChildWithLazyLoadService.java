package org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr03_eager_vs_lazy_load.pr03_01_lazy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
