package com.packtpub.vertx.footballplayermicroservice.dao;

import com.packtpub.vertx.footballplayermicroservice.model.FootballPlayer;
import io.vertx.core.Future;
import io.vertx.core.file.FileSystem;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.ext.sql.SQLOptions;
import io.vertx.ext.sql.UpdateResult;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * DAO class that implements the JDBC methods needed to implement the CRUD
 * services.
 *
 * @author Mauro Vocale
 * @version 1.0.0 12/10/2018
 */
public class FootballPlayerDAO {

    public Future<FootballPlayer> insert(SQLConnection connection,
            FootballPlayer footballPlayer,
            boolean closeConnection) {
        Future<FootballPlayer> future = Future.future();
        String sql
                = "INSERT INTO football_player (name, surname, age, team, position, price) VALUES (?, ?, ?, ?, ?, ?)";
        connection.updateWithParams(sql,
                new JsonArray().add(
                        footballPlayer.getName()).add(footballPlayer.
                        getSurname())
                        .add(footballPlayer.getAge()).add(footballPlayer.
                        getTeam())
                        .add(footballPlayer.getPosition()).add(footballPlayer.
                        getPrice().intValue()),
                ar -> {
            if (closeConnection) {
                connection.close();
            }
            future.handle(ar.map(res -> new FootballPlayer(res.getKeys().
                    getInteger(0),
                    footballPlayer.getName(), footballPlayer.getSurname(),
                    footballPlayer.getAge(), footballPlayer.getTeam(),
                    footballPlayer.getPosition(), footballPlayer.getPrice()))
            );
        }
        );
        return future;
    }

    public Future<SQLConnection> connect(JDBCClient jdbc) {
        Future<SQLConnection> future = Future.future();
        jdbc.getConnection(ar -> future.handle(ar.map(c -> c.setOptions(
                new SQLOptions().setAutoGeneratedKeys(true))
        )
        )
        );
        return future;
    }

    public Future<List<FootballPlayer>> query(SQLConnection connection) {
        Future<List<FootballPlayer>> future = Future.future();
        connection.query("SELECT * FROM football_player", result -> {
            connection.close();
            future.handle(
                    result.map(rs -> rs.getRows().stream().map(
                    FootballPlayer::new).
                    collect(Collectors.toList()))
            );
        }
        );
        return future;
    }

    public Future<FootballPlayer> queryOne(SQLConnection connection, String id) {
        Future<FootballPlayer> future = Future.future();
        String sql = "SELECT * FROM football_player WHERE id = ?";
        connection.
                queryWithParams(sql, new JsonArray().add(Integer.valueOf(id)),
                        result -> {
                    connection.close();
                    future.handle(
                            result.map(rs -> {
                                List<JsonObject> rows = rs.getRows();
                                if (rows.isEmpty()) {
                                    throw new NoSuchElementException(
                                            "No article with id " + id);
                                } else {
                                    JsonObject row = rows.get(0);
                                    return new FootballPlayer(row);
                                }
                            })
                    );
                });
        return future;
    }

    public Future<FootballPlayer> update(SQLConnection connection, String id,
            FootballPlayer footballPlayer) {
        Future<FootballPlayer> future = Future.future();
        String sql
                = "UPDATE football_player SET name = ?, surname = ?, age = ?, team = ?, position = ?, price = ? WHERE id = ?";
        connection.updateWithParams(sql,
                new JsonArray().add(footballPlayer.getName()).add(
                        footballPlayer.getSurname())
                        .add(footballPlayer.getAge()).add(footballPlayer.
                        getTeam())
                        .add(footballPlayer.getPosition()).add(footballPlayer.
                        getPrice().intValue())
                        .add(Integer.valueOf(id)),
                ar -> {
            connection.close();
            if (ar.failed()) {
                future.fail(ar.cause());
            } else {
                UpdateResult ur = ar.result();
                if (ur.getUpdated() == 0) {
                    future.fail(new NoSuchElementException(String.format(
                            "No football player with id %s",
                            id)));
                } else {
                    future.handle(ar.map(res -> new FootballPlayer(
                            res.getKeys().
                                    getInteger(0),
                            footballPlayer.getName(), footballPlayer.
                            getSurname(),
                            footballPlayer.getAge(), footballPlayer.getTeam(),
                            footballPlayer.getPosition(), footballPlayer.
                            getPrice()))
                    );
                }
            }
        });
        return future;
    }

    public Future<Void> delete(SQLConnection connection, String id) {
        Future<Void> future = Future.future();
        String sql = "DELETE FROM football_player WHERE id = ?";
        connection.updateWithParams(sql,
                new JsonArray().add(Integer.valueOf(id)),
                ar -> {
            connection.close();
            if (ar.failed()) {
                future.fail(ar.cause());
            } else {
                if (ar.result().getUpdated() == 0) {
                    future.fail(new NoSuchElementException("Unknown article "
                            + id));
                } else {
                    future.complete();
                }
            }
        }
        );
        return future;
    }

    public Future<SQLConnection> createSomeDataIfNone(FileSystem fileSystem,
            SQLConnection connection) {
        Future<SQLConnection> future = Future.future();
        connection.query("SELECT * FROM football_player", select -> {
            if (select.failed()) {
                future.fail(select.cause());
            } else {
                if (select.result().getResults().isEmpty()) {
                    fileSystem.readFile("data.sql", ar -> {
                        if (ar.failed()) {
                            future.fail(ar.cause());
                        } else {
                            connection.execute(ar.result().toString(),
                                    ar2 -> future.handle(ar2.map(connection))
                            );
                        }
                    });
                } else {
                    future.complete(connection);
                }
            }
        });
        return future;
    }
    
    public Future<SQLConnection> createTableIfNeeded(FileSystem fileSystem, SQLConnection connection) {
        Future<SQLConnection> future = Future.future();
        fileSystem.readFile("schema.sql", ar -> {
            if (ar.failed()) {
                future.fail(ar.cause());
            } else {
                connection.execute(ar.result().toString(),
                        ar2 -> future.handle(ar2.map(connection))
                );
            }
        });
        return future;
    }

}
