-- ================================================================================
-- Create Security Policy
-- Going forward we will need to add a security policy for any tables that need to be tenant (or organization) specific.
-- This basically apply row security for sample_entity table (based on organization_id column)
CREATE SECURITY POLICY SampleEntitySecPolicy
    ADD FILTER PREDICATE Security.fn_security_predicate(organization_id) ON dbo.sample_entity,
    ADD BLOCK PREDICATE Security.fn_security_predicate(organization_id) ON dbo.sample_entity
    WITH (STATE = ON);
GO