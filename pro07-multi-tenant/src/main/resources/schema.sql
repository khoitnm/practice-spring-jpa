-- /////////////////////////////////////////////////////////////////////////
-- This script will run when we start the application.
-- Please read more in: https://docs.spring.io/spring-boot/docs/current/reference/html/howto.html#howto-database-initialization
-- /////////////////////////////////////////////////////////////////////////

-- ================================================================================
-- Add security predicate data
-- Please view more at row level security: https://docs.microsoft.com/en-us/sql/relational-databases/security/row-level-security?view=sql-server-ver15
CREATE SCHEMA security;

-- Explanation about 'GO' statement:
-- https://docs.microsoft.com/en-us/sql/t-sql/language-elements/sql-server-utilities-statements-go?view=sql-server-ver15
GO

/**
 @param @tenant_col is the column name which is used as a tenant_id.
  It's basically the column used for data discrimination as mentioned in Multi-Tenant DB: https://docs.jboss.org/hibernate/orm/4.3/devguide/en-US/html/ch16.html#d5e4808
 */
CREATE FUNCTION security.fn_security_predicate(@tenant_col AS sysname)
    RETURNS TABLE WITH SCHEMABINDING AS
        RETURN SELECT 1 AS fn_securitypredicate_result
               WHERE @tenant_col = USER_NAME()
                  -- The login username is actually the tenant_id (aka organization_id)
                  -- You can see that logic in MultiTenantConnectionProviderImpl.setUser(connection, tenantId)

                  OR IS_ROLEMEMBER('db_ddladmin') = 1;

-- ================================================================================
-- Create sample_entity table.
GO
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
)


-- ================================================================================
-- Create Security Policy
-- Going forward we will need to add a security policy for any tables that need to be tenant (or organization) specific.
GO
CREATE SECURITY POLICY SampleEntitySecPolicy
    ADD FILTER PREDICATE Security.fn_security_predicate(organization_id) ON dbo.sample_entity,
    ADD BLOCK PREDICATE Security.fn_security_predicate(organization_id) ON dbo.sample_entity
    WITH (STATE = ON);
