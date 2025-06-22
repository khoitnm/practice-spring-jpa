package org.tnmk.practice_spring_jpa.pro08_multi_tenant_session_context.business;

import jakarta.persistence.*;
import lombok.Data;
import org.tnmk.practice_spring_jpa.pro08_multi_tenant_session_context.common.multitenant.jpa_auto_set_tenant.EntitySupportTenant;
import org.tnmk.practice_spring_jpa.pro08_multi_tenant_session_context.common.multitenant.jpa_auto_set_tenant.TenantEntityListener;

@Table(name = "sample_entity")
@Entity
@Data

/**
 * This is just an optional configuration, not mandatory.
 * Please view {@link TenantEntityListener} for more details the benefit of this configuration.
 */
@EntityListeners(TenantEntityListener.class)
public class SampleEntity implements EntitySupportTenant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "organization_id", updatable = false)
    private String organizationId;
}
