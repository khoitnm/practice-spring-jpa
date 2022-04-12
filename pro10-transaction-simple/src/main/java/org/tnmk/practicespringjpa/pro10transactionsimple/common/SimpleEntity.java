package org.tnmk.practicespringjpa.pro10transactionsimple.common;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.Temporal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TemporalType;
import java.time.OffsetDateTime;
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

  @Column(name = "description")
  private String description;

  @Column(name = "created_date_time", columnDefinition = "TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP(6)", updatable = false)
  private OffsetDateTime createdDateTime;

  @Column(name = "update_date_time", columnDefinition = "TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6)")
  private OffsetDateTime updateDateTime;

  public SimpleEntity(String name) {
    this.name = name;
  }
}
