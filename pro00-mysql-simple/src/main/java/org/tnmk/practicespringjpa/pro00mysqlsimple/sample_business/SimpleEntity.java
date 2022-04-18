package org.tnmk.practicespringjpa.pro00mysqlsimple.sample_business;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

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
  // FIXME There's a problem with "TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP(6)"
  //  When using "DEFAULT CURRENT_TIMESTAMP(6)", the generated Timestamp will be saved at UTC.
  //  So loading into entity, it will be loaded as it is and won't convert that UTC into local time,
  //  hence it will cause problem.
  //
  //  Because of that, we don't use "DEFAULT CURRENT_TIMESTAMP(6)" anymore, we have to rely on @CreationTimestamp instead.
  //  When using that, it will generate createdDateTime at application level. It means dateTime will be generated in local timezone,
  //  and hence saved into DB at local timezone.
  @Column(name = "created_date_time", columnDefinition = "TIMESTAMP(6)", updatable = false)
  @CreationTimestamp
  private ZonedDateTime createdDateTime;

  public SimpleEntity() {
  }

  public SimpleEntity(String name) {
    this.name = name;
  }
}
