package org.tnmk.practice_spring_jpa.pro08_multi_tenant_session_context.business;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "sample_entity")
@Entity
@Data
public class SampleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "organization_id", updatable = false)
    private String organizationId;
}
