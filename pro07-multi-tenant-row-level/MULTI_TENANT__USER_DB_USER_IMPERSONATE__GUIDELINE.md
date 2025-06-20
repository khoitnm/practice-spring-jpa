# Behind the Scenes: How Hibernate Multitenancy Works

This guide explains the inner workings of the Hibernate multitenancy implementation in this project.

---

## 1. **Row-Level Multitenancy Overview**

In this approach, data of all tenants is stored in the same database tables, but each tenant's data is filtered based on
a unique identifier (tenantId).
This approach allows simpler infrastructure setup and management, as it avoids the complexity of managing multiple
databases or schemas.

---

## 2. **Key Components**

### 2.1. [

`HibernateConfig.java`](
src/main/java/org/tnmk/practice_spring_jpa/pro07_multi_tenant_row_level/common/multitenant/HibernateConfig.java)

Configure Hibernate to support multitenancy at row level (discrepancy).

### 2.2. [

`SecurityContext.java`](
src/main/java/org/tnmk/practice_spring_jpa/pro07_multi_tenant_row_level/common/security/SecurityContext.java)

This class helps client code set which tenant the current thread is working with.
It provides a way to store and retrieve the current tenantId throughout the business flow lifecycle.

### 2.3. [

`CurrentTenantIdentifierResolverImpl`](
src/main/java/org/tnmk/practice_spring_jpa/pro07_multi_tenant_row_level/common/multitenant/CurrentTenantIdentifierResolverImpl.java)

In your main business logic, when you query data, it will use this class to determine which tenantId is currently
active.

### 2.4. [

`MultiTenantConnectionProviderImpl`](
src/main/java/org/tnmk/practice_spring_jpa/pro07_multi_tenant_row_level/common/multitenant/MultiTenantConnectionProviderImpl.java)

- The `getConnection(String tenantId)` method is responsible for returning a connection associated with the given
  tenant.
    - It basically set that tenantId into database connections (by using `EXECUTE AS USER = :tenantId;`).
    - Of courses, before that, it needs to create the corresponding Db User for that tenantId (a tenantId is used as a
      Db User), and the password of that user is the same as the password of DB login
      (Note that DB Login vs. Db User are different concepts in SQL Server).
- `releaseConnection(String tenantId, Connection connection)` method is responsible for releasing the connection back to
  the DB connection pool.
    - Before that, it needs to reset the tenantId in the connection (by using `REVERT;`). If this step fails, the
      connection still keep the tenantId context, and hence when the next thread run, it may reuse the same DB
      connection with the wrong tenantId context, very dangerous.

---

## 3. **Database-Level Security**

### 3.1 Row-Level Security

- **Function**: The `security.fn_security_predicate` function ensures that only rows matching the tenant ID are
  accessible.
- **Logic**:
    - The tenant ID is compared with the current database user (`USER_NAME()`).
    - Admin roles (e.g., `db_ddladmin`) bypass this restriction.

### 3.2 Schema Binding

- The `WITH SCHEMABINDING` clause ensures that the function is tightly coupled with the database schema, improving
  performance and security.

## 4. **Performance Considerations**

Please view [MULTI_TENANT_PERFORMANCE.md](MULTI_TENANT__USER_DB_USER_IMPERSONATE__PERFORMANCE.md) for performance
considerations and stress tests.