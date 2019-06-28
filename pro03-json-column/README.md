Some DB will support JSON column such as MySQL or PostgreSQL.
I prefer MongoDB for fully support JSON instead of using MySQL with JSON column. 
However, there are some cases that we don't want more complexity for DevOps teams to work with, so we went with MySQL and JSON column.

This project will demonstrate how to define a JSON column and how to test it with embedded DB which support JSON column.

# Important Note when working with JSON Column
## Testing by using Embedded MySQL
- HSQL or H2DB are very conveninent for testing, but they don't support JSON column, unfortunately.
- For Embedded MySQL: we can use either `com.wix:wix-embedded-mysql-download-and-extract` or `io.zonky.test:embedded-database-spring-test`
- For Embedded PostgreSQL: OpenTable Embedded PostgreSQL Component (https://github.com/opentable/otj-pg-embedded)

## Implement equals() and hashCode()
We must implement the `equals()` and `hashCode()` for the model which will be stored as JSON.
For example:
```
ParentEntity (table sample_entity)
 |__ChildEntity (stored as JSON column in the table sample_entity)
```
If we don't override `equals()` and `hashCode()` in `ChildEntity`, when Hibernate executes insert or select a `ParentEntity`, it will execute an additional update `ParentEntity` statement.
(you can see that in the log).
We can simple fix that problem by implementing `equals()` and `hashCode()` in `ChildEntity`.


