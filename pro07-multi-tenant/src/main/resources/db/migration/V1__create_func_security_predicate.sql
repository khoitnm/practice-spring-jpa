-- ================================================================================
-- Add security predicate data
-- Please view more at row level security: https://docs.microsoft.com/en-us/sql/relational-databases/security/row-level-security?view=sql-server-ver15
-- CREATE SCHEMA security;
-- GO
-- 'GO' statement: basically define each batch process.
-- https://docs.microsoft.com/en-us/sql/t-sql/language-elements/sql-server-utilities-statements-go?view=sql-server-ver15

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