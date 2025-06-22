# Hibernate Multitenancy Practice

This is a practice project for learning
about [Hibernate Multitenancy](https://docs.jboss.org/hibernate/orm/current/userguide/html_single/Hibernate_User_Guide.html#multitenacy)
at row level.

Note:

- This project is using [DB Session Context](./MULTI_TENANT__SESSION_CONTEXT__GUIDELINE.md) which has better performance than
  DB User Impersonate (`EXECUTE AS USER = :tenantId;`), because it doesn't need to create a DB User for each tenantId,

## Prerequisites

- Java 21 or higher (I use virtual thread in the test, you can change the test case to use regular thread if using lower Java version).
- Maven.
- DB MS SQL Server.

## Run project

- Start MS SQL Server by one of the following approaches:
    - Using docker: [.\docker\mssql\start-mssql.bat](.\docker\mssql\start-mssql.bat).
    - Using a local installation of MS SQL Server.
- Configure your database connection in `application.yml` if necessary.
- Run one of its test cases (e.g. `SampleServiceTest.java`) to test it functions.
  The test cases will connect to our local MS SQL Server.
  We don't use in-memory DB because it may not work exactly the same way as MS SQL Server,
  and also don't use Docker container because it's too slow to start.

# Reference doc

Guideline:
- https://docs.jboss.org/hibernate/orm/current/userguide/html_single/Hibernate_User_Guide.html#multitenacy
- https://docs.jboss.org/hibernate/orm/4.3/devguide/en-US/html/ch16.html#d5e4817

