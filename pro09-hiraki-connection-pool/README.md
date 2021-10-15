# Introduction
We are practice some configuration for Hiraki Connection Pool:
Usually after every 30 seconds, we can see Hiraki Connection Pool status (based on the log configured in logback.xml):
``` 
2021-10-15 13:39:31.502 DEBUG 1560 --- [ool housekeeper] com.zaxxer.hikari.pool.HikariPool        : pro09-hiraki-connection-pool - Pool stats (total=1, active=1, idle=0, waiting=0)
```

When there is a connection open longer than a configured threshold (`leak-detection-threshold`), it will show a log like this:
``` 
java.lang.Exception: Apparent connection leak detected
	at com.zaxxer.hikari.HikariDataSource.getConnection(HikariDataSource.java:128)
	at org.tnmk.practicespringjpa.pro09hirakiconnectionpool.repository.ConnectionLeakRepository.createConnectionWithoutClosing(ConnectionLeakRepository.java:20)
	at org.tnmk.practicespringjpa.pro09hirakiconnectionpool.repository.ConnectionLeakRepository$$FastClassBySpringCGLIB$$edf753b8.invoke(<generated>)
```

# Some good document about Hiraki connection pool
My personal document: https://docs.google.com/document/d/10A_Zw2_s5JZ7qN4HaS97zh8a2uFwDtbCLTM7YCNybxI/edit#

https://github.com/brettwooldridge/HikariCP

## Connection Pool Size - Formula:
https://github.com/brettwooldridge/HikariCP/wiki/About-Pool-Sizing
The formula below is provided by the PostgreSQL project as a starting point, but we believe it will be largely applicable across databases. You should test your application, i.e. simulate expected load, and try different pool settings around this starting point:

connections = ((core_count * 2) + effective_spindle_count)

A formula which has held up pretty well across a lot of benchmarks for years is
that for optimal throughput the number of active connections should be somewhere
near ((core_count * 2) + effective_spindle_count). Core count should not include
HT threads, even if hyperthreading is enabled. Effective spindle count is zero if
the active data set is fully cached, and approaches the actual number of spindles
as the cache hit rate falls. ... There hasn't been any analysis so far regarding
how well the formula works with SSDs.
Guess what that means? Your little 4-Core i7 server with one hard disk should be running a connection pool of: 9 = ((4 * 2) + 1). Call it 10 as a nice round number. Seem low? Give it a try, we'd wager that you could easily handle 3000 front-end users running simple queries at 6000 TPS on such a setup. If you run load tests, you will probably see TPS rates starting to fall, and front-end response times starting to climb, as you push the connection pool much past 10 (on that given hardware).

