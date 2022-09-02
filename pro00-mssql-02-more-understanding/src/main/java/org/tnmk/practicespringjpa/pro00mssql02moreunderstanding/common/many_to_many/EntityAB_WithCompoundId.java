package org.tnmk.practicespringjpa.pro00mssql02moreunderstanding.common.many_to_many;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@IdClass(CompoundABId.class)
@Table(name = "entity_ab_with_compound_id")
@Data
public class EntityAB_WithCompoundId {
  @Id
  @Column(name = "user_id")
  private Integer userId;

  @Id
  @Column(name = "iam_role_name")
  private String iamRoleName;
}
