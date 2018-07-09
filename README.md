# I. Introduction
Of courses this practice is used for demo connecting to DB. But the main reason for this project is I want to see the generated SQL script from the entity classes.  

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
