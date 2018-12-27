package com.packtpub.vertx.footballplayermicroservice.service;

import com.packtpub.vertx.footballplayermicroservice.dao.FootballPlayerDAO;
import static com.packtpub.vertx.footballplayermicroservice.helper.ActionHelper.*;
import com.packtpub.vertx.footballplayermicroservice.model.FootballPlayer;
import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

/**
 * Verticle class that exposes our RESTFUL API.
 *
 * @author Mauro Vocale
 * @version 1.0.0 12/10/2018
 */
public class FootballPlayerVerticle extends AbstractVerticle {

    private JDBCClient jdbc;

    @Override
    public void start(Future<Void> fut) {

        // Create a router object.
        Router router = Router.router(vertx);

        // Bind "/" to our hello message - so we are still compatible.
        router.route("/").handler(routingContext -> {
            HttpServerResponse response = routingContext.response();
            response
                    .putHeader("content-type", "text/html")
                    .end("<h1>Football Player Vert.x 3 microservice application</h1>");
        });

        router.get("/footballplayer").handler(this::getAll);
        router.get("/footballplayer/show/:id").handler(this::getOne);
        router.route("/footballplayer*").handler(BodyHandler.create());
        router.post("/footballplayer/save").handler(this::addOne);
        router.delete("/footballplayer/delete/:id").handler(this::deleteOne);
        router.put("/footballplayer/update/:id").handler(this::updateOne);

        ConfigStoreOptions fileStore = new ConfigStoreOptions()
                .setType("file")
                .setFormat("json")
                .setConfig(new JsonObject().put("path",
                        "src/main/conf/my-application-conf.json"));

        ConfigRetrieverOptions options = new ConfigRetrieverOptions().addStore(
                fileStore);
        ConfigRetriever retriever = ConfigRetriever.create(vertx, options);

        // Start sequence:
        // 1 - Retrieve the configuration
        //      |- 2 - Create the JDBC client
        //      |- 3 - Connect to the database (retrieve a connection)
        //              |- 4 - Create table if needed
        //                   |- 5 - Add some data if needed
        //                          |- 6 - Close connection when done
        //              |- 7 - Start HTTP server
        //      |- 9 - we are done!
        ConfigRetriever.getConfigAsFuture(retriever)
                .compose(config -> {
                    jdbc = JDBCClient.createShared(vertx, config,
                            "My-Reading-List");
                    FootballPlayerDAO dao = new FootballPlayerDAO();
                    return dao.connect(jdbc)
                            .compose(connection -> {
                                Future<Void> future = Future.future();
                                createTableIfNeeded(connection)
                                        .compose(this::createSomeDataIfNone)
                                        .setHandler(x -> {
                                            connection.close();
                                            future.handle(x.mapEmpty());
                                        });
                                return future;
                            })
                            .compose(v -> createHttpServer(config, router));

                })
                .setHandler(fut);
    }

    private Future<Void> createHttpServer(JsonObject config, Router router) {
        Future<Void> future = Future.future();
        vertx
                .createHttpServer()
                .requestHandler(router::accept)
                .listen(
                        config.getInteger("HTTP_PORT", 8080),
                        res -> future.handle(res.mapEmpty())
                );
        return future;
    }

    private Future<SQLConnection> createTableIfNeeded(SQLConnection connection) {
        FootballPlayerDAO dao = new FootballPlayerDAO();
        return dao.createTableIfNeeded(vertx.fileSystem(), connection);
    }

    private Future<SQLConnection> createSomeDataIfNone(SQLConnection connection) {
        FootballPlayerDAO dao = new FootballPlayerDAO();
        return dao.createSomeDataIfNone(vertx.fileSystem(), connection);
    }

    // ---- HTTP Actions ----
    private void getAll(RoutingContext rc) {
        FootballPlayerDAO dao = new FootballPlayerDAO();
        dao.connect(jdbc)
                .compose(dao::query)
                .setHandler(ok(rc));
    }

    private void addOne(RoutingContext rc) {
        FootballPlayer footballPlayer = rc.getBodyAsJson().mapTo(
                FootballPlayer.class);
        FootballPlayerDAO dao = new FootballPlayerDAO();
        dao.connect(jdbc)
                .compose(connection -> dao.insert(connection, footballPlayer,
                true))
                .setHandler(created(rc));
    }

    private void deleteOne(RoutingContext rc) {
        String id = rc.pathParam("id");
        FootballPlayerDAO dao = new FootballPlayerDAO();
        dao.connect(jdbc)
                .compose(connection -> dao.delete(connection, id))
                .setHandler(noContent(rc));
    }

    private void getOne(RoutingContext rc) {
        String id = rc.pathParam("id");
        FootballPlayerDAO dao = new FootballPlayerDAO();
        dao.connect(jdbc)
                .compose(connection -> dao.queryOne(connection, id))
                .setHandler(ok(rc));
    }

    private void updateOne(RoutingContext rc) {
        String id = rc.request().getParam("id");
        FootballPlayer footballPlayer = rc.getBodyAsJson().mapTo(
                FootballPlayer.class);
        FootballPlayerDAO dao = new FootballPlayerDAO();
        dao.connect(jdbc)
                .compose(connection -> dao.
                update(connection, id, footballPlayer))
                .setHandler(created(rc));
    }

}
