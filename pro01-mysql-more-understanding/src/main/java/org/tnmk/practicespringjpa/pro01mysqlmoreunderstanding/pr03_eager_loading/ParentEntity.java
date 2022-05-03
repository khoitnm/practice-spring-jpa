package org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr03_eager_loading;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "parent")
@Data
@NoArgsConstructor
public class ParentEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  private String name;

  public ParentEntity(String name) {
    this.name = name;
  }
}
