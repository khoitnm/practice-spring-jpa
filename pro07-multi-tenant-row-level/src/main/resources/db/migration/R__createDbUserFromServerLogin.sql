/**
  Assume that we already have a system login `demo_user` created on the server, now we'll need to create corresponding database user from that server login
  so that other tenant can impersonate this user.

  It means we have to CREATE USER sa first before we can `GRANT IMPERSONATE ON USER::[01] TO [sa];`
  If we don't do this, it will throw an error like this: "cannot find the user 'sa' in the database, or you don't have permission.":
 */
IF NOT EXISTS (SELECT 1 FROM sys.database_principals WHERE name = 'demo_user')
BEGIN
    CREATE USER [demo_user] FOR LOGIN [demo_user];
END;


IF EXISTS (SELECT 1 FROM sys.database_principals WHERE name = '01') BEGIN
    -- If a user was impersonated from a server login like this:
    -- GRANT IMPERSONATE ON USER::[01] TO scdb_local;

    -- You'll have to revoke IMPERSONATE first:
    REVOKE IMPERSONATE ON USER::[01] FROM [demo_user];

    -- Then you can drop the user:
    DROP USER [01];
END