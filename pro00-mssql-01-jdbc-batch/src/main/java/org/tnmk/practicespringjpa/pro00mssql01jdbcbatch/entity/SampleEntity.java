package org.tnmk.practicespringjpa.pro00mssql01jdbcbatch.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;
import java.time.ZonedDateTime;

@Entity
@Table(name = "sample_entity")
@Data
public class SampleEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  private String name;

  @Column(name = "starting_date_time", columnDefinition = "DATETIME")
  private ZonedDateTime startingDateTime;
  /**
   * The columnDefinition here is specific for MS SQL Server only. It won't work with other kinds of DBs such as My SQL, Oracle.
   */
  @Column(name = "creation_dateTime", updatable = false, columnDefinition = "datetime2 DEFAULT getdate()")
  @CreationTimestamp
  private Instant createdDateTime;

  @Column(name = "update_dateTime")
  @UpdateTimestamp
  private ZonedDateTime updateDateTime;
}
