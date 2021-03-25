package org.tnmk.practicespringjpa.pro07multitenant.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

/**
 * NOTE that we can have 2 entities classes associated to the same table.
 */
@Entity
@Table(name = "sample_entity")
@Setter
@Getter
public class SampleEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id")
  private Long id;

  private String name;

  /**
   * The columnDefinition here is specific for MS SQL Server only. It won't work with other kinds of DBs such as My SQL, Oracle.
   */
  @Column(name = "creation_dateTime", updatable = false, columnDefinition = "datetime2 DEFAULT getdate()")
  @CreationTimestamp
  private Instant createdDateTime;

  @Column(name = "update_dateTime")
  @UpdateTimestamp
  private Instant updateDateTime;
}
