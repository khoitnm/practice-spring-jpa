# I. Introduction
You can import those modules independently or you can import all of them by importing the parent project.

1. `pro01-simple-entity`: Start the Application, it will generate the SQL for creating tables on the log.
2. `pro02-json-column`: Table with JSON column. This sample shows how to write the converter for column, and how to run test with embedded DB which supports JSON column.
3. `pro03-db-container-for-test`: When running the test, we don't use an embedded DB anymore. We may want to connect a DB with exactly version with the Prod DB. 
So when running the test, we will automatically start a DB Docker Container in this practice.
 
# II. Build projects
Run the command line, it will compile the source code, build project, and then run tests.
```
mvn clean install 
```

If you want to build without testing, run the command line:
```
mvn clean install -DskipTests 
```

# III. Start Application.

Start the application inside a module:
```
mvn spring-boot:run 
``` 
Or you can use:
```
java -jar target/pro01-simple-entity-0.0.1-SNAPSHOT.jar 
```

To stop it, press `Ctrl-C`
