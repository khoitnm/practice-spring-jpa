/**
  Script to create 30,000 database users in SQL Server.
  After that, we can test `CreateDbUserStressTest.java` to create many users in parallel and see how it impacts the database performance.
 */
DECLARE @i INT = 1;

WHILE @i <= 30000
BEGIN
    DECLARE @username NVARCHAR(50) = NEWID();
    DECLARE @sql NVARCHAR(MAX);

    SET @sql = 'CREATE USER [' + @username + '] FOR LOGIN [demo_user];';

EXEC sp_executesql @sql;

    SET @i = @i + 1;
END;