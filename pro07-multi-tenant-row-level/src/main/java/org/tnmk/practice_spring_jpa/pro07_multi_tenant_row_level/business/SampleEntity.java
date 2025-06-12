package org.tnmk.practice_spring_jpa.pro07_multi_tenant_row_level.business;

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
}
