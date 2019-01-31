package com.packtpub.vertx.footballplayermicroservice;

import com.packtpub.vertx.footballplayermicroservice.model.FootballPlayer;
import com.packtpub.vertx.footballplayermicroservice.service.FootballPlayerVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.junit5.Timeout;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import java.math.BigInteger;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(VertxExtension.class)
public class TestFootballPlayerVerticle {

    @BeforeEach
    void deploy_verticle(Vertx vertx, VertxTestContext testContext) {
        vertx.deployVerticle(new FootballPlayerVerticle(), testContext.
                succeeding(id -> testContext.completeNow()));
    }

    @Test
    @DisplayName("Should start a Web Server on port 8080 and the GET all API"
            + "returns an array of 24 elements")
    @Timeout(value = 10, timeUnit = TimeUnit.SECONDS)
    void findAll(Vertx vertx, VertxTestContext testContext) throws
            Throwable {
        System.out.println("FIND ALL *****************");
        vertx.createHttpClient().getNow(8080, "localhost", "/footballplayer",
                response -> testContext.verify(() -> {
                    assertTrue(response.statusCode() == 200);
                    response.bodyHandler(body -> {
                        JsonArray array = new JsonArray(body);
                        assertTrue(23 == array.size());
                        testContext.completeNow();
                    });
                }));
    }

    @Test
    @DisplayName(
            "Should start a Web Server on port 8080 and, using the POST API,"
            + "insert a new football player")
    @Timeout(value = 10, timeUnit = TimeUnit.SECONDS)
    public void create(Vertx vertx, VertxTestContext context) {
        System.out.println("CREATE *****************");
        final String json = Json.
                encodePrettily(new FootballPlayer(null, "Mauro", "Vocale", 38,
                        "Juventus", "central midfielder", new BigInteger("100")));
        final String length = Integer.toString(json.length());
        vertx.createHttpClient().post(8080, "localhost", "/footballplayer/save")
                .putHeader("content-type", "application/json")
                .putHeader("content-length", length)
                .handler(response -> {
                    assertTrue(response.statusCode() == 201);
                    assertTrue(response.headers().get("content-type").
                            contains("application/json"));
                    response.bodyHandler(body -> {
                        final FootballPlayer footballPlayer = Json.decodeValue(
                                body.toString(),
                                FootballPlayer.class);
                        assertTrue(footballPlayer.getName().equalsIgnoreCase(
                                "Mauro"));
                        assertTrue(footballPlayer.getAge() == 38);
                        assertTrue(footballPlayer.getId() != null);
                        context.completeNow();
                    });
                })
                .write(json)
                .end();
    }

    @Test
    @DisplayName("Should start a Web Server on port 8080 and, using the PUT API"
            + "update a  football player")
    @Timeout(value = 10, timeUnit = TimeUnit.SECONDS)
    public void edit(Vertx vertx, VertxTestContext context) {
        System.out.println("EDIT *****************");
        // Create the record to update: in order to make easy to test I don't
        // read the record from the database but I easily build a new record
        // with an existing ID.
        final String json = Json.
                encodePrettily(new FootballPlayer(1, "Gianluigi", "Buffon", 40,
                        "PSG", "Goalkeeper", new BigInteger("25")));
        final String length = Integer.toString(json.length());
        vertx.createHttpClient().put(8080, "localhost",
                "/footballplayer/update/1")
                .putHeader("content-type", "application/json")
                .putHeader("content-length", length)
                .handler(response -> {
                    assertTrue(response.statusCode() == 201);
                    assertTrue(response.headers().get("content-type").
                            contains("application/json"));
                    response.bodyHandler(body -> {
                        final FootballPlayer footballPlayer = Json.decodeValue(
                                body.toString(),
                                FootballPlayer.class);
                        assertTrue(footballPlayer.getName().equalsIgnoreCase(
                                "Gianluigi"));
                        assertTrue(footballPlayer.getPrice().intValue() == 25);
                        assertTrue(footballPlayer.getTeam().equalsIgnoreCase(
                                "PSG"));
                        context.completeNow();
                    });
                })
                .write(json)
                .end();
    }

    @Test
    @DisplayName(
            "Should start a Web Server on port 8080 and, using the GET findOne API"
            + "returns the specified football player")
    @Timeout(value = 10, timeUnit = TimeUnit.SECONDS)
    void findOne(Vertx vertx, VertxTestContext testContext) throws
            Throwable {
        System.out.println("FIND One *****************");
        vertx.createHttpClient().getNow(8080, "localhost",
                "/footballplayer/show/1",
                response -> testContext.verify(() -> {
                    assertTrue(response.statusCode() == 200);
                    response.bodyHandler(body -> {
                        FootballPlayer footballPlayer = Json.decodeValue(body,
                                FootballPlayer.class);
                        assertTrue(footballPlayer.getName().equalsIgnoreCase(
                                "Gianluigi"));
                        assertTrue(footballPlayer.getPrice().intValue() == 2);
                        assertTrue(footballPlayer.getTeam().equalsIgnoreCase(
                                "Paris Saint Germain"));
                        assertTrue(footballPlayer.getId() == 1);
                        testContext.completeNow();
                    });
                }));
    }

    @Test
    @DisplayName(
            "Should start a Web Server on port 8080 and, using the DELETE API,"
            + "removes the specified football player")
    @Timeout(value = 10, timeUnit = TimeUnit.SECONDS)
    void delete(Vertx vertx, VertxTestContext testContext) throws
            Throwable {
        System.out.println("DELETE *****************");
        vertx.createHttpClient().delete(8080, "localhost",
                "/footballplayer/delete/1")
                .putHeader("content-type", "application/json")
                .handler(response -> {
                    assertTrue(response.statusCode() == 204);
                    testContext.completeNow();
                })
                .end();
    }
}
