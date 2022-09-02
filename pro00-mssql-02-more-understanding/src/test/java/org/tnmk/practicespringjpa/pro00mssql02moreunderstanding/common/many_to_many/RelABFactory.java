package org.tnmk.practicespringjpa.pro00mssql02moreunderstanding.common.many_to_many;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class RelABFactory {
  private final RelABRepository repository;

  public static List<RelAB> randomABRels(int aId, int numberOfRelationsToAId) {
    List<RelAB> relAB_s = IntStream.range(0, numberOfRelationsToAId)
        .mapToObj(i -> {
          int bId = new Random().nextInt();
          return createABRel(aId, bId);
        })
        .collect(Collectors.toList());
    return relAB_s;
  }

  public static RelAB createABRel(int aId, int bId) {
    RelAB relAB = new RelAB();
    relAB.setAId(aId);
    relAB.setBId(bId);
    return relAB;
  }
}
