package pro07_multi_tenant_row_level.business;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class SampleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
}
