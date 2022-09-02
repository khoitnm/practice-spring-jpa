package org.tnmk.practicespringjpa.pro00mssql02moreunderstanding.common.many_to_many;

import lombok.Data;
import org.tnmk.practicespringjpa.pro00mssql02moreunderstanding.common.parent_child.ParentEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "entity_a")
@Data
public class EntityA {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  private String name;
}
