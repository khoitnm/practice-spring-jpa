-- Create sample_entity table.
create table dbo.sample_entity (
    id                 bigint identity not null,
    creation_date_time datetime2 DEFAULT getdate(),
    name               varchar(255),
    organization_id    SYSNAME NOT NULL,
    -- Cannot use SESSION_CONTEXT(N'tenant_id') in a column default constraint in SQL Server. Default constraints only allow constant expressions or functions that do not depend on session or context.
    -- That's why you can consider using a trigger to set the organization_id (however, I got a bit problem when using this, and haven't spend time to investigate more about it yet, so I just manually set that value when inserting data)
    update_date_time   datetime2,
    primary key (id)
);
GO

/* I intended to use trigger to automatically get tenant_id from SESSION_CONTEXT and set it into the DB table, but it doesn't work.
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
*/

CREATE SECURITY POLICY SampleEntitySecPolicy
    ADD FILTER PREDICATE tenant.fn_tenant_filter(organization_id)  ON dbo.sample_entity,
    ADD BLOCK PREDICATE tenant.fn_tenant_filter(organization_id) ON dbo.sample_entity
    WITH (STATE = ON);
GO

