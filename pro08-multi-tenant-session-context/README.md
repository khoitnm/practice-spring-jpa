# Hibernate Multitenancy Practice

This is a practice project for learning
about [Hibernate Multitenancy](https://docs.jboss.org/hibernate/orm/current/userguide/html_single/Hibernate_User_Guide.html#multitenacy)
at row level.

Note:

- This project is using [DB Session Context](./MULTI_TENANT__SESSION_CONTEXT__GUIDELINE.md) which is better than
  DB User Impersonate (`EXECUTE AS USER = :tenantId;`), because it doesn't need to create a DB User for each tenantId,

## Features

- Row-level multitenancy with Hibernate.
- Configuration of `MultiTenantConnectionProvider` and `CurrentTenantIdentifierResolver`.
- Example of tenant-based data segregation.

## Prerequisites

- Java 21 or higher (I use virtual thread in the test).
- Maven.
- DB MS SQL Server.

## Run project

- Start MS SQL Server by one of the following approaches:
    - Using docker: [.\docker\mssql\start-mssql.bat](.\docker\mssql\start-mssql.bat).
    - Using a local installation of MS SQL Server.
- Configure your database connection in `application.yml` if necessary.
- Run the application using: `mvn spring-boot:run`

## Run test cases

- Please follow the above steps to start MS SQL Server first, the test cases will connect to our local MS SQL Server.
- We don't use in-memory DB because it may not work exactly the same way as MS SQL Server,
  and also don't use Docker container because it's too slow to start.

# Reference doc

Guideline:

- https://docs.jboss.org/hibernate/orm/current/userguide/html_single/Hibernate_User_Guide.html#multitenacy
- https://docs.jboss.org/hibernate/orm/4.3/devguide/en-US/html/ch16.html#d5e4817

