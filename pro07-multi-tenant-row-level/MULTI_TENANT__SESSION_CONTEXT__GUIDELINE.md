# Multi-Tenant Application Architecture with SQL Server - Session Context

## Overview

This guide explains how to implement multi-tenancy at the **application level** using:

- A shared SQL Server database
- A shared schema
- Application-managed tenant access control
- Optional SQL Server Row-Level Security (RLS) for automatic tenant isolation

---

## Why Application-Level Multi-Tenancy?

| Criteria        | DB User per Tenant | App-Level Multi-Tenancy |
|-----------------|--------------------|-------------------------|
| Scalability     | Poor               | Excellent               |
| Simplicity      | Complex            | Simple                  |
| Security        | Fragile            | Strong + enforceable    |
| Maintainability | Painful            | Straightforward         |

> Creating a DB user per tenant does **not scale**. Application-level isolation is the best practice.

---

## Data Model Design

### 1. Add `tenant_id` to All Business Tables

```sql
ALTER TABLE Customers
    ADD tenant_id UNIQUEIDENTIFIER NOT NULL;
ALTER TABLE Orders
    ADD tenant_id UNIQUEIDENTIFIER NOT NULL;
```

> Ensure `tenant_id` is present and required in all rows that need tenant-level ownership.

---

### 2. Create a `Tenants` Registry Table

```sql
CREATE TABLE Tenants
(
    tenant_id   UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
    tenant_name NVARCHAR(255) NOT NULL,
    created_at  DATETIME                     DEFAULT GETDATE()
);
```

---

## Application-Side Enforcement

### 1. Determine the Current Tenant at Login

The application determines which tenant the current user belongs to and stores it in the session context (or JWT token,
etc.).

### 2. Inject `tenant_id` into Every Query

```sql
SELECT *
FROM Orders
WHERE tenant_id = @tenant_id;
```

> This must be done **automatically**, ideally by a middleware, service layer, or ORM filter.

---

## SQL Server Row-Level Security (RLS)

### Why Use RLS?

To **guarantee** that all queries are automatically scoped to the correct tenant — even if a developer forgets the
`WHERE tenant_id = ...`.

---

### Step 1: Create the Tenant Filter Function

```sql
CREATE FUNCTION dbo.fn_tenant_filter()
    RETURNS UNIQUEIDENTIFIER
WITH SCHEMABINDING
         AS
BEGIN
RETURN SESSION_CONTEXT(N'tenant_id');
END
```

> This function retrieves the tenant ID from the current session.

---

### Step 2: Apply Row-Level Security Policy

```sql
CREATE
SECURITY POLICY OrderSecurityPolicy
ADD FILTER PREDICATE dbo.fn_tenant_filter() = tenant_id ON dbo.Orders
WITH (STATE = ON);
```

> This policy automatically applies to all `SELECT`, `UPDATE`, `DELETE` operations on `Orders`.

---

### Step 3: Set `tenant_id` at Runtime

In your application, after opening a DB connection:

```sql
EXEC sp_set_session_context @key = N'tenant_id', @value = @CurrentTenantId;
```

- Must be called once per connection/session.
- Works with connection pools.

---

## Summary Behind the Scenes: How It All Works

| Component              | Role                                                               |
|------------------------|--------------------------------------------------------------------|
| `tenant_id` column     | Tags each row with the owning tenant                               |
| Application code       | Authenticates users and injects `tenant_id` into SQL or session    |
| `SESSION_CONTEXT`      | Stores `tenant_id` for the current DB connection                   |
| RLS Predicate Function | Reads `tenant_id` from session to filter rows automatically        |
| SQL Server Engine      | Internally rewrites all queries to include `WHERE tenant_id = ...` |

---

## What Happens If You Forget the Tenant Filter?

### Without RLS:

```sql
-- Dangerous!
SELECT *
FROM Orders;
-- ➜ Exposes all tenant data!
```

### With RLS:

```sql
-- Safe!
SELECT *
FROM Orders;
-- ➜ Internally becomes:
-- SELECT * FROM Orders WHERE tenant_id = SESSION_CONTEXT('tenant_id')
```

---

## Example in Action

```sql
-- Set the current tenant ID (app must do this)
EXEC sp_set_session_context @key = N'tenant_id', @value = '123e4567-e89b-12d3-a456-426614174000';

-- Run a query
SELECT *
FROM Orders;
-- ➜ Returns only Orders where tenant_id = '123e4567...'
```

---

## Best Practices

| Practice                                    | Why It Matters                                 |
|---------------------------------------------|------------------------------------------------|
| Always use `tenant_id` in your schema       | Core to enforcing data isolation               |
| Use `SESSION_CONTEXT` + RLS                 | Prevents accidental cross-tenant access        |
| Apply global ORM filters (e.g. EF Core)     | Consistency across all queries                 |
| Prevent clients from submitting `tenant_id` | App must enforce it, not the end user          |
| Use automated testing                       | Test that each tenant sees only their own data |

---

## Optional Variants

| Use Case                                    | Option                                      |
|---------------------------------------------|---------------------------------------------|
| Need strict isolation per tenant            | Use separate schemas or databases           |
| SaaS with thousands of tenants (small data) | Shared schema with RLS                      |
| Tenants require custom features             | Use extension tables + `tenant_feature_map` |

---

## Summary

- **Application-level multi-tenancy** is the best practice.
- Add `tenant_id` to your data model.
- Inject `tenant_id` into all queries via code or ORM.
- Use **Row-Level Security (RLS)** to enforce safety at the database level.
- Always set `SESSION_CONTEXT('tenant_id')` before running tenant-sensitive queries.

