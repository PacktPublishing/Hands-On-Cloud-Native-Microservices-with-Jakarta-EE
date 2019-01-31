# JPA, JAX-RS, CDI and JTA .war Example of Microservice application that supports the Long Running Action microprofile specification.

This example takes a normal JPA, CDI, JAX-RS and JTA build, and wraps it into a -swarm runnable jar.

It uses a MySQL database. In order to run the project you need to install MySQL docker image using the following command:

```console
$ docker run --name mysql_thorntail -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -d mysql --default-authentication-plugin=mysql_native_password
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
    <configuration>
        <properties>
            <lra.http.host>${lra.http.host}</lra.http.host>
            <lra.http.port>${lra.http.port}</lra.http.port>
            <swarm.port.offset>600</swarm.port.offset>
        </properties>
    </configuration>
</plugin>
```

We let the plugin define the parts of Thorntail that we need based on the APIs our code uses, so we don't need to define any dependencies explicitly.
Run

You can run it many ways:

```console
    $ mvn package && java -jar target/football-player-offer-microservice-lra-thorntail.jar -Dlra.http.port=8580
```

Use

http://localhost:8680/footballplayeroffer
