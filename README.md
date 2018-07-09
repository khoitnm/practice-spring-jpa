# I. Introduction
The project using Spring Event. It's the abstract development model that we can switch between Application Event (sync & async), JMS, or even Kafka.

# II. Build projects
Run the command line, it will complie the source code, build project, and then run tests.
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
java -jar target/pro01-application-event-0.0.1-SNAPSHOT.jar 
```

To stop it, press `Ctrl-C`
