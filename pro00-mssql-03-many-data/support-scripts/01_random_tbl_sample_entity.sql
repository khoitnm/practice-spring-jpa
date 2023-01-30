-- Guideline:
-- - https://stackoverflow.com/questions/48776162/efficient-way-to-generate-2-billion-rows-in-sql-server-2014-developer
-- - https://dba.stackexchange.com/questions/130392/generate-and-insert-1-million-rows-into-simple-table/130408#130408?newreg=9ffc5d60fee94a428a82d529bdda6ca3

-- Inserted 200K items within 4-5 seconds.
-- Inserted 10M items within 2:28 minutes.
-- Inserted 30M items within 5:44 minutes.
-- Inserted 50M items within 8:51 minutes (no index for expiration_date column).
-- Inserted 50M items within 22:30 minutes (with index for expiration_date column).
-- Inserted 100M items: When using Docker, I got error: "Could not allocate a new page for database 'master' because of insufficient disk space in filegroup 'PRIMARY'. Create the necessary space by dropping objects in the filegroup, adding additional files to the filegroup, or setting autogrowth on for existing files in the filegroup.")

-- Note: don't set totalrows too high; otherwise, we will get error:
--  The transaction log for database 'xxx' is full due to 'ACTIVE_TRANSACTION'.
--  And when getting that error, I have to install MSSQL Server on my local machine instead of using Docker.

SET
NOCOUNT ON;

DECLARE
@InitOffset int = 0;    -- Start offset by 0.
                        -- Then in the second run, increase it to 50m (== totalrows of each run).

DECLARE
@now datetime = getutcdate(),
@message_type int = 1,
@org_id int = 1;


WITH t2 AS (SELECT n FROM (VALUES (0), (0)) t(n)),                                          -- table with 2 rows
     t3 AS (SELECT n FROM (VALUES (0), (0), (0)) t(n)),                                     -- table with 3 rows
     t5 AS (SELECT n FROM (VALUES (0), (0), (0), (0), (0)) t(n)),                           -- table with 5 rows
     t7 AS (SELECT n FROM (VALUES (0), (0), (0), (0), (0), (0), (0)) t(n)),                 -- table with 7 rows
     t10 AS (SELECT n FROM (VALUES (0), (0), (0), (0), (0), (0), (0), (0), (0), (0)) t(n)), -- table with 10 rows
     t1k AS (-- table with 1000 rows
         SELECT 0 AS n
         FROM t10 AS tmp10
                  CROSS JOIN t10 AS tmp100
                  CROSS JOIN t10 AS tmp1000),
     t1m AS (-- table with 1 million rows
         SELECT 0 AS n
         FROM t1k AS tmp1000
                  CROSS JOIN t1k AS tmp1m
     ),
     t10m AS (SELECT 0 AS n
              FROM t1m
                       CROSS JOIN t10 AS tmp10m
     ),

     /*
      This table contains the total rows that will be inserted into the table in each execution.
      */
     totalrows AS (SELECT 0 AS n
                   FROM t10m
     ),

     CrossJoinData AS (SELECT ROW_NUMBER() OVER(ORDER BY (SELECT NULL)) AS num FROM totalrows)

INSERT
INTO dbo.sample_entity WITH(TABLOCKX)
SELECT
       'entity_code_' + CAST((num + @InitOffset) as nvarchar(20)),
       'name_' + CAST((num + @InitOffset) as nvarchar(24)), -- name
FROM CrossJoinData;
RAISERROR
('%d rows inserted',0, 0, @@ROWCOUNT) WITH NOWAIT;

