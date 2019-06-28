# Introduction

### What is ddl script:
Ddl script will store all SQL scripts for creating DB structure.

### Why do we need ddl script:
If you look at other sub-modules, we don't need to generate any ddl scripts. 
Spring Boot Data will automatically create DB structure based on annotation in Entity classes.

However, in some cases, we want to disable that feature from Spring framework and create DB schema based on SQL script (mostly because we want to manage DB schema by some other tools such as Flyway).
And to make our life easier, we have some tools to generate SQL script from those Entity classes.

### Where is ddl script generated:
In `./target/generated-resources/sql/dll/` 