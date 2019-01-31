# API Gateway simulator Example of Microservice Long Running Action application.

This example builds a coarse grained controller that performs the interactions with the football-player-microservice-lra and football-player-offer-microservice-lra microservices.

It uses a PostgreSQL database. In order to run the project you need to install PostgreSQL docker image using the following command:

```console
$ docker run --name postgres_thorntail  -e POSTGRES_PASSWORD=postgresPwd -e POSTGRES_DB=football_players_registry -d -p 5532:5432 postgres
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
            <swarm.port.offset>400</swarm.port.offset>
        </properties>
    </configuration>
</plugin>
```

We let the plugin define the parts of Thorntail that we need based on the APIs our code uses, so we don't need to define any dependencies explicitly.
Run

You can run it many ways:

```console
    $ mvn package && java -jar target/football-player-market-microservice-lra-thorntail.jar -Dlra.http.port=8580
```

Use

http://localhost:8480/footballplayer-market
