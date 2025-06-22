-- Create sample_entity table.
create table dbo.sample_entity
(
    id                 bigint identity not null,
    creation_date_time datetime2 DEFAULT getdate(),
    name               varchar(255),
    organization_id    SYSNAME         NOT NULL
        CONSTRAINT df_sample_entity_tenant_id DEFAULT CURRENT_USER,
    -- The login username is actually the tenant_id (aka organization_id)
    -- You can see that logic in MultiTenantConnectionProviderImpl.setUser(connection, tenantId)
    update_date_time   datetime2,
    primary key (id)
);
GO

CREATE SECURITY POLICY SampleEntitySecPolicy
    ADD FILTER PREDICATE security.fn_tenant_filter(organization_id)  ON dbo.sample_entity,
    ADD BLOCK PREDICATE security.fn_tenant_filter(organization_id) ON dbo.sample_entity
    WITH (STATE = ON);
GO