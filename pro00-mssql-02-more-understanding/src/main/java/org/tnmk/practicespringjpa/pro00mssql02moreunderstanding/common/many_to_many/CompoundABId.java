package org.tnmk.practicespringjpa.pro00mssql02moreunderstanding.common.many_to_many;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class CompoundABId implements Serializable {

  private int entityAId;
  private int entityBId;
}
