package org.tnmk.practicespringjpa.pro00mssql02moreunderstanding.common.parent_child;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.Instant;
import java.time.ZonedDateTime;

@Entity
@Table(name = "child_entity")
@Data
public class ChildEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  private ParentEntity parentEntity;
}
