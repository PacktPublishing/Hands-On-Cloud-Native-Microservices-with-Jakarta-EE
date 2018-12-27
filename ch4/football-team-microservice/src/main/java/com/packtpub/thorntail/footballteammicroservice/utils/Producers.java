package com.packtpub.thorntail.footballteammicroservice.utils;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import java.util.Arrays;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

/**
 * CDI Producers neeed to build and inject the MongoClient.
 * 
 * @author Mauro Vocale
 * @version 1.0.0 22/07/2018
 */
public class Producers {

    @Produces
    @ApplicationScoped
    public MongoClient createMongoClient() {
        System.out.println("Creating MongoClient");
        String dbName = System.getenv("DB_NAME");
        if (dbName == null || dbName.isEmpty()) {
            System.out.println(
                    "Could not get environment variable DB_NAME using the default value of 'FootballTeamRegistry'");
            dbName = "FootballTeamRegistry";
        }

        String dbServerParam = System.getenv("DB_SERVER");
        final String dbServer = (dbServerParam != null && !dbServerParam.
                isEmpty()) ? dbServerParam : "localhost";

        String dbUsername = System.getenv("DB_USERNAME");
        String dbPassword = System.getenv("DB_PASSWORD");
        if (dbUsername != null && !dbUsername.isEmpty() && dbPassword != null
                && !dbPassword.isEmpty()) {
            System.out.println(String.format(
                    "Connecting to MongoDB %s@%s using %s user credentials",
                    dbName, dbServer, dbUsername));

            MongoCredential credential
                    = MongoCredential.createCredential(dbUsername, dbName,
                            dbPassword.toCharArray());

            return MongoClients.create(
                    MongoClientSettings.builder()
                            .applyToClusterSettings(builder -> builder.hosts(
                            Arrays.asList(new ServerAddress(dbServer, 27017))))
                            .credential(credential)
                            .build());

        } else {
            System.out.println(String.format(
                    "Connecting to MongoDB %s@%s without authentication", dbName,
                    dbServer));
            return MongoClients.create(
                    MongoClientSettings.builder()
                            .applyToClusterSettings(builder -> builder.hosts(
                            Arrays.asList(new ServerAddress(dbServer))))
                            .build());

        }

    }
}
