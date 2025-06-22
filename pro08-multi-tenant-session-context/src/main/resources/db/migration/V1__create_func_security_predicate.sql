-- ================================================================================
-- Add security predicate data
-- Please view more at row level security: https://docs.microsoft.com/en-us/sql/relational-databases/security/row-level-security?view=sql-server-ver15
CREATE SCHEMA security;
GO
-- 'GO' statement: basically define each batch process.
-- https://docs.microsoft.com/en-us/sql/t-sql/language-elements/sql-server-utilities-statements-go?view=sql-server-ver15

/**
 @param @tenant_val_from_tble is the value from a table column which is used as a tenant_id.
  It's basically the column used for data discrimination as mentioned in Multi-Tenant DB: https://docs.jboss.org/hibernate/orm/4.3/devguide/en-US/html/ch16.html#d5e4808
 */
CREATE FUNCTION security.fn_tenant_filter(@tenant_val_from_tble)
    RETURNS TABLE WITH SCHEMABINDING AS
        RETURN SELECT 1 AS fn_tenant_filter_result WHERE SESSION_CONTEXT(N'tenant_id') = @tenant_val_from_tble
-- The login username is actually the tenant_id (aka organization_id)
-- You can see that logic in MultiTenantConnectionProviderImpl.setUser(connection, tenantId)
-- OR IS_ROLEMEMBER('db_ddladmin') = 1; -- If you are connecting with `sa`, you probably will have this db_ddladmin role