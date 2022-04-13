package org.tnmk.practicespringjpa.pro01simplemysql.sample_business;

import lombok.Data;

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
public class SimpleEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  private String name;

  // TIMESTAMP(6) will include millis in timestamp
  // And this field is saved and loaded as it is (it won't actumatically convert to UTC before saving).
  //
  // FIXME However, when using  DEFAULT CURRENT_TIMESTAMP(6), the generated Timestamp will be saved at UTC.
  //  So loading into entity, it will be loaded as it is and won't convert that UTC into local time,
  //  hence it will cause problem.
  @Column(name = "created_date_time", columnDefinition = "TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP(6)", updatable = false)
  private ZonedDateTime createdDateTime;

  public SimpleEntity() {
  }

  public SimpleEntity(String name) {
    this.name = name;
  }
}
