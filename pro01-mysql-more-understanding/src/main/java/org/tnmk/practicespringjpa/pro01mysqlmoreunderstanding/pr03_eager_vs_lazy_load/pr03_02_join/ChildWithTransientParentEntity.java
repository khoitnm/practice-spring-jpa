package org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr03_eager_vs_lazy_load.pr03_02_join;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr03_eager_vs_lazy_load.common.ParentEntity;

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
public class ChildWithTransientParentEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "name")
  private String name;

  @Transient
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_id")
  private ParentEntity parentEntity;

  public ChildWithTransientParentEntity(String name) {
    this.name = name;
  }
}
