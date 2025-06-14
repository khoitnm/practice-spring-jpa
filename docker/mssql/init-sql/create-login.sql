CREATE LOGIN [demo_user] WITH PASSWORD = 'Password1';
CREATE USER [demo_user] FROM LOGIN [demo_user];
ALTER ROLE db_owner ADD MEMBER [demo_user];
ALTER ROLE db_ddladmin ADD MEMBER [demo_user];