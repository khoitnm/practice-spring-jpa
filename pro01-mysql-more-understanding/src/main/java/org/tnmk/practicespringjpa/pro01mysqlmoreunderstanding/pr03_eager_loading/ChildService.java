package org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr03_eager_loading;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChildService {

  private final ChildRepository childRepository;

  @Transactional
  public Optional<ChildEntity> findChildById(long childId) {
    return childRepository.findById(childId);
  }
}
