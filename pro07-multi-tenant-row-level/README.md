# Hibernate Multitenancy Practice

This is a practice project for learning
about [Hibernate Multitenancy](https://docs.jboss.org/hibernate/orm/current/userguide/html_single/Hibernate_User_Guide.html#multitenacy)
at row level.

Note:

- This project is currently use [DB User Impersonate](./MULTI_TENANT__USER_DB_USER_IMPERSONATE__GUIDELINE.md) approach.
- However, I think using [DB Session Context](./MULTI_TENANT__SESSION_CONTEXT__GUIDELINE.md) is better, because it
  doesn't need to create a DB User for each tenantId, and has better performance.
  Please read that document to have a better comparison.
  When having time, I will refactor this project to use the DB Session Context approach.

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
    - Using a local installation of MS SQL Server. Then run initial
      scripts [.\docker\mssql\manual-init.bat](.\docker\mssql\manual-init.bat) to create prerequisite users for this
      practice
      (please also update information about DB connection accordingly).
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

