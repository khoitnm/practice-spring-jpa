# I. Introduction
You can import those modules independently or you can import all of them by importing the parent project.
Please view the README in each sub-module to understand more.
 
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
