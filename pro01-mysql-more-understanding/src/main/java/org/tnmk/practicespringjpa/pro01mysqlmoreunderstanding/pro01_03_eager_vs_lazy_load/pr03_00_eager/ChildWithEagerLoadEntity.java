package org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pro01_03_eager_vs_lazy_load.pr03_00_eager;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pro01_03_eager_vs_lazy_load.common.ParentEntity;

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
public class ChildWithEagerLoadEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "name")
  private String name;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "parent_id")
  private ParentEntity parentEntity;

  public ChildWithEagerLoadEntity(String name) {
    this.name = name;
  }
}
