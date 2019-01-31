# JAX-RS and CDI .war Example of Microservice application that uses a NOSQL database.

This example takes a normal CDI and JAX-RS build, and wraps it into a -swarm runnable jar.

It uses a MongoDB database. In order to run the project you need to install MongoDB docker image using the following command:

```console
$ docker run --name mongo_thorntail -p 27017:27017 -d mongo
```

Project pom.xml


The project adds a <plugin> to configure thorntail-maven-plugin to create the runnable .jar.

```xml
<plugin>
    <groupId>io.thorntail</groupId>
    <artifactId>thorntail-maven-plugin</artifactId>
    <version>${version.thorntail}</version>
    <executions>
    	<execution>
        	<goals>
            	<goal>package</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

We let the plugin define the parts of Thorntail that we need based on the APIs our code uses, so we don't need to define any dependencies explicitly.
Run

You can run it many ways:

```console
    $ mvn package && java -jar target/football-team-microservice-thorntail.jar
    $ mvn thorntail:run
```

Use

http://localhost:8280/footballteam
