package org.tnmk.practicespringjpa.pro00mssql04combinequerymodel.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;
import java.time.ZonedDateTime;

@Entity
@Table(name = "parent_entity")
@Data
public class ParentEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  private String name;
}
