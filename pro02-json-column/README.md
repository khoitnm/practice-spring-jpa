Some DB will support JSON column such as MySQL or PostgreSQL.
I prefer MongoDB for fully support JSON instead of using MySQL with JSON column. 
However, there are some cases that we don't want more complexity for DevOps teams to work with, so we went with MySQL and JSON column.

This project will demonstrate how to define a JSON column and how to test it with embedded DB which support JSON column: 
OpenTable Embedded PostgreSQL Component (https://github.com/opentable/otj-pg-embedded)