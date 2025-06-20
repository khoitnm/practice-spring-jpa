# Summary

> The `CREATE USER` DDL query in MS SQL Server performs poorly under high workloads and with many DB users.
> Stress tests show significant performance degradation as the number of DB users increases,
> with execution times rising from ~10 ms (10 users) to ~1200 ms (30K users) and many errors read timeout.

# Details

As you see
in [MULTI_TENANT__USER_DB_USER_IMPERSONATE__GUIDELINE.md](MULTI_TENANT__USER_DB_USER_IMPERSONATE__GUIDELINE.md), the
process need to
`CREATE USER` for each
tenantId, and then `EXECUTE AS USER = :tenantId;` to set the tenantId context for the current thread.
That `CREATE USER` statement is a DDL query, and MS SQL Server is not designed to execute DDL queries in parallel
effectively (under high workload), and also not design to have too many DB Users.

Please take a look at the stress
test [CreateDbUserStressTest.java](src/test/java/org/tnmk/practice_spring_jpa/pro07_multi_tenant_row_level/common/CreateDbUserStressTest.java),
the results are:

- When DB has only 10 DB
  Users: [createDbUserStressTestLog_10Users_5threads_4loops.txt](createDbUserStressTestLog_10Users_5threads_4loops.txt)
    - With just 5 theads, 4 loops per thread, each `CREATE USER` takes about 10 ms (normal speed).
- When DB has 30K DB
  Users: [createDbUserStressTestLog_30KUsers_5threads_4loops.txt](createDbUserStressTestLog_30KUsers_5threads_4loops.txt) (
  summary result is at the end of the log file)
    - With just 5 theads, 4 loops per thread, each `CREATE USER` takes about 1.2 seconds (way too slow)
      because this statement normally only need less than 20 milliseconds.

