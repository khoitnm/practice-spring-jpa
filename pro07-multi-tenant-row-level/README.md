# Hibernate Multitenancy Practice

This is a practice project for learning
about [Hibernate Multitenancy](https://docs.jboss.org/hibernate/orm/current/userguide/html_single/Hibernate_User_Guide.html#multitenacy)
at row level.

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
    - Using docker: [..\docker\mssql\start-mssql.bat](..\docker\mssql\start-mssql.bat).
    - Using a local installation of MS SQL Server. Then run initial
      scripts [..\docker\mssql\manual-init.bat](..\docker\mssql\manual-init.bat) to create prerequisite users for this
      practice
      (please also update information about DB connection accordingly).
- Configure your database connection in `application.yml` if necessary.
- Run the application using: `mvn spring-boot:run`

##  

# Reference doc

Guideline:

- https://docs.jboss.org/hibernate/orm/current/userguide/html_single/Hibernate_User_Guide.html#multitenacy
- https://docs.jboss.org/hibernate/orm/4.3/devguide/en-US/html/ch16.html#d5e4817
