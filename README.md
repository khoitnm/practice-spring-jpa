You need to start your MySQL server on your local machine before running this application.

# References
Lock config with Spring Boot JPA:
https://www.baeldung.com/java-jpa-transaction-locks 
https://dzone.com/articles/concurrency-and-locking-with-jpa-everything-you-ne

Optimistic vs. Pessimistic Lock
https://stackoverflow.com/questions/35523428/spring-jpa-lock <-- An easy to understand explanation
https://stackoverflow.com/questions/129329/optimistic-vs-pessimistic-locking#:~:targetText=Optimistic%20Locking%20is%20a%20strategy,you%20write%20the%20record%20back.
https://vladmihalcea.com/a-beginners-guide-to-java-persistence-locking/
"If database locking is sufficient for batch processing systems, a multi-request web flow spans over several database transactions. For long conversations, a logical (optimistic) locking mechanism is much more appropriate."

Deadlock
http://www.itec.uni-klu.ac.at/~harald/CSE/Content/deadlock.html

=> Don't prefer using Pessimistic Lock (asking other threads to wait) if unnecessary because it can cause deadlock?

Select... for update (For Exclusive lock): 
- https://www.baeldung.com/jpa-pessimistic-locking
- Used in Flyway: https://flywaydb.org/blog/flyway-4.1.0.html
"While Flyway has always worked great with multiple application nodes by using careful SELECT ... FOR UPDATE metadata table locking to ensure consistency, this didn't work as well for a completely empty database where there were no records to lock or where not even the metadata table (or the schema!) had already been created."

Solution for running a long process on only one server instance (Migrating Events):
- Requirement: inserting into DB records must be wait until all events are migrated. 
- Solution: Use unique key on the process name combine with INSERT (to claim process instance).
That way, the second instant cannot insert the record, therefore cannot claim the process and have to waiting for another instance to finish its process.
The waiting process will know when another process is finished by a cronjob query, but a better way is a notification.
But notification also takes time, when sending or receiving notification, another record may already processed and inserted into DB.
 
Lock entire table: 
- Cannot lock entire table with JPA: https://stackoverflow.com/questions/32336481/table-exclusive-lock-with-jpa
- But we can do it with MySQL: https://dev.mysql.com/doc/refman/8.0/en/lock-tables.html (so that other microservices instants won't be able to update/insert data while migrating).
However, even if we do it this way, after finishing migrating. Other old microservice instants still be able to add new data, and those new data won't be migrating.
-> When applying rolling deployment, we'll do rolling migrating.
