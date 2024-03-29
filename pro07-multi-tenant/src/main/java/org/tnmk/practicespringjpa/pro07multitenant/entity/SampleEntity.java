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
 * Note: the organization_id which is used as tenant_id (for data discrimination) won't need to be defined here.
 * You can see it inside {@link schema.sql}
 */
@Entity
@Table(name = "sample_entity")
@Setter
@Getter
public class SampleEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
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
