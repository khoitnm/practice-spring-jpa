package org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr03_eager_loading;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "child")
@Data
@NoArgsConstructor
public class ChildEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "name")
  private String name;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "parent_id")
  private ParentEntity parentEntity;

  public ChildEntity(String name) {
    this.name = name;
  }
}
