CREATE LOGIN [dbUser] WITH PASSWORD = 'Passw0rd!';
CREATE USER [dbUser] FROM LOGIN [dbUser];
ALTER ROLE db_owner ADD MEMBER [dbUser];