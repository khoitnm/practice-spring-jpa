package org.tnmk.practicespringjpa.pro10transactionsimple.common;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sample_entity")
@Data
public class SimpleEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  private String name;

  public SimpleEntity() {
  }

  public SimpleEntity(String name) {
    this.name = name;
  }
}
