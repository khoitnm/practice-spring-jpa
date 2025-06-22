-- Create sample_entity table.
create table dbo.sample_entity
(
    id                 bigint identity not null,
    creation_date_time datetime2 DEFAULT getdate(),
    name               varchar(255),
    organization_id SYSNAME NOT NULL,
        -- Cannot use SESSION_CONTEXT(N'tenant_id') in a column default constraint in SQL Server. Default constraints only allow constant expressions or functions that do not depend on session or context.
        -- That's why we use a trigger to set the organization_id.
        -- CONSTRAINT df_sample_entity_tenant_id DEFAULT SESSION_CONTEXT(N'tenant_id'),
    update_date_time   datetime2,
    primary key (id)
);
GO

CREATE TRIGGER trg_set_organization_id
    ON dbo.sample_entity
    INSTEAD OF INSERT
AS
BEGIN
INSERT INTO dbo.sample_entity (creation_date_time, name, organization_id, update_date_time)
SELECT
    ISNULL(i.creation_date_time, GETDATE()),
    i.name,
    ISNULL(i.organization_id, CONVERT(SYSNAME, SESSION_CONTEXT(N'tenant_id'))),
    i.update_date_time
FROM inserted i;
END
GO

CREATE SECURITY POLICY SampleEntitySecPolicy
    ADD FILTER PREDICATE tenant.fn_tenant_filter(organization_id)  ON dbo.sample_entity,
    ADD BLOCK PREDICATE tenant.fn_tenant_filter(organization_id) ON dbo.sample_entity
    WITH (STATE = ON);
GO

