package org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr03_eager_loading;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChildService {

  private final ChildRepository childRepository;

  //  @Transactional
  public Optional<ChildEntity> findChildById(long childId) {
    return childRepository.findById(childId);
  }

  public List<ChildEntity> findByIds(List<Long> childIds) {
    return childRepository.findByIdIn(childIds);
  }
}
