package org.tnmk.practicespringjpa.pro10transactionsimple.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.ZonedDateTime;

@Entity
@Table(name = "sample_entity")
@Data
@NoArgsConstructor
public class SimpleEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "name", unique = true)
  private String name;

  @Column(name = "created_date_time", columnDefinition = "TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP(6)", updatable = false)
  private ZonedDateTime createdDateTime;

  public SimpleEntity(String name) {
    this.name = name;
  }
}
