package com.packtpub.vertx.footballplayermicroservice.service;

import com.packtpub.vertx.footballplayermicroservice.dao.FootballPlayerReactiveDAO;
import static com.packtpub.vertx.footballplayermicroservice.helper.ActionHelperReactive.*;
import com.packtpub.vertx.footballplayermicroservice.model.FootballPlayer;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.CompletableHelper;
import io.vertx.reactivex.config.ConfigRetriever;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.core.http.HttpServerResponse;
import io.vertx.reactivex.ext.jdbc.JDBCClient;
import io.vertx.reactivex.ext.sql.SQLConnection;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.RoutingContext;
import io.vertx.reactivex.ext.web.handler.BodyHandler;

/**
 * Verticle class that exposes our RESTFUL API in a reactive manner.
 *
 * @author Mauro Vocale
 * @version 1.0.0 15/10/2018
 */
public class FootballPlayerReactiveVerticle extends AbstractVerticle {

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
                    .end("<h1>Football Player Reactive Vert.x 3 microservice application</h1>");
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
        FootballPlayerReactiveDAO dao = new FootballPlayerReactiveDAO();

        retriever.rxGetConfig()
                .doOnSuccess(config -> jdbc = JDBCClient.createShared(vertx,
                config, "My-Reading-List"))
                .flatMap(config -> dao.connect(jdbc)
                .flatMap(connection -> this.createTableIfNeeded(connection)
                .flatMap(this::createSomeDataIfNone)
                .doAfterTerminate(connection::close)
                )
                .map(x -> config)
                )
                .flatMapCompletable(config -> createHttpServer(config, router))
                .subscribe(CompletableHelper.toObserver(fut));
    }

    private Completable createHttpServer(JsonObject config,
            Router router) {
        return vertx
                .createHttpServer()
                .requestHandler(router::accept)
                .rxListen(config.getInteger("HTTP_PORT", 8080))
                .toCompletable();
    }

    private Single<SQLConnection> createTableIfNeeded(SQLConnection connection) {
        FootballPlayerReactiveDAO dao = new FootballPlayerReactiveDAO();
        return dao.createTableIfNeeded(vertx.fileSystem(), connection);
    }

    private Single<SQLConnection> createSomeDataIfNone(SQLConnection connection) {
        FootballPlayerReactiveDAO dao = new FootballPlayerReactiveDAO();
        return dao.createSomeDataIfNone(vertx.fileSystem(), connection);
    }

    // ---- HTTP Actions ----
    private void getAll(RoutingContext rc) {
        FootballPlayerReactiveDAO dao = new FootballPlayerReactiveDAO();
        dao.connect(jdbc)
                .flatMap(dao::query)
                .subscribe(ok(rc));
    }

    private void addOne(RoutingContext rc) {
        FootballPlayer footballPlayer = rc.getBodyAsJson().mapTo(
                FootballPlayer.class);
        FootballPlayerReactiveDAO dao = new FootballPlayerReactiveDAO();
        dao.connect(jdbc)
                .flatMap(connection -> dao.insert(connection, footballPlayer,
                true))
                .subscribe(created(rc));
    }

    private void deleteOne(RoutingContext rc) {
        String id = rc.pathParam("id");
        FootballPlayerReactiveDAO dao = new FootballPlayerReactiveDAO();
        dao.connect(jdbc)
                .flatMapCompletable(connection -> dao.delete(connection, id))
                .subscribe(noContent(rc), onError(rc));
    }

    private void getOne(RoutingContext rc) {
        FootballPlayerReactiveDAO dao = new FootballPlayerReactiveDAO();
        String id = rc.pathParam("id");
        dao.connect(jdbc)
                .flatMap(connection -> dao.queryOne(connection, id))
                .subscribe(ok(rc));
    }

    private void updateOne(RoutingContext rc) {
        String id = rc.request().getParam("id");
        FootballPlayer footballPlayer = rc.getBodyAsJson().mapTo(
                FootballPlayer.class);
        FootballPlayerReactiveDAO dao = new FootballPlayerReactiveDAO();
        dao.connect(jdbc)
                .flatMap(connection -> dao.update(connection, id,
                footballPlayer))
                .subscribe(created(rc));
    }
}
