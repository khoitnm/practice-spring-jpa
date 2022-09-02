package org.tnmk.practicespringjpa.pro00mssql02moreunderstanding.common.many_to_many;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@IdClass(RelABId.class)
@Table(name = "ab_rel")
@Data
public class RelAB {
  @Id
  @Column(name = "a_id")
  private int entityAId;

  @Id
  @Column(name = "b_id")
  private int entityBId;
}
