# JPA, JAX-RS, CDI and JTA .war Example of Microservice application

This example takes a normal JPA, CDI, JAX-RS and JTA build, and wraps it into a -swarm runnable jar.

It uses a MySQL database. In order to run the project you need to install MySQL docker image using the following command:

```console
$ docker run --name mysql_thorntail -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=football_managers_registry -d mysql --default-authentication-plugin=mysql_native_password
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
    $ mvn package && java -jar target/football-manager-microservice-thorntail.jar
    $ mvn thorntail:run
```

Use

http://localhost:8180/footballmanager
