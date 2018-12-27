package com.packtpub.thorntail.footballteammicroservice.service;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.packtpub.thorntail.footballteammicroservice.model.Team;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

/**
 * Class that acts as a DAO that manages the interactions with the MongoDB.
 * 
 * @author Mauro Vocale
 * @version 1.0.0 22/07/2018
 */
@ApplicationScoped
public class MongoTeamServiceImpl implements TeamService {
    
    private static final List<Team> DEFAULT_TEAM_LIST = new ArrayList<>();
    static {
        DEFAULT_TEAM_LIST.add(new Team("Mauryno_NewTeam", "Turin",
                1, new TreeSet<>(Arrays.asList(1, 4, 6, 7, 10,
                        12, 14, 16, 18, 19, 20))));
        DEFAULT_TEAM_LIST.add(new Team("Foogaro_Great_Lazie", "Rome",
                2, new TreeSet<>(Arrays.asList(2, 5, 8, 9, 11,
                        13, 15, 17, 21, 22, 23))));
        
    }

    @Inject
    private MongoClient mc;

    private MongoCollection<Document> teamCollection;

    @PostConstruct
    protected void init() {
        System.out.println("@PostConstruct is called...");

        String dbName = System.getenv("DB_NAME");
        if (dbName == null || dbName.isEmpty()) {
            System.out.println(
                    "Could not get environment variable DB_NAME using the default value of "
                    + "'football_team_registry'");
            dbName = "football_team_registry";
        }

        MongoDatabase db = mc.getDatabase(dbName);

        teamCollection = db.getCollection("teams");

        // Drop the collection if it exists and then add default content
        teamCollection.drop();
        addAll(DEFAULT_TEAM_LIST);

    }

    @PreDestroy
    protected void destroy() {
        System.out.println("Closing MongoClient connection");
        if (mc != null) {
            mc.close();
        }
    }

    @Override
    public List<Team> getTeams() {
        return StreamSupport.stream(teamCollection.find().spliterator(),
                false)
                .map(d -> toTeam(d))
                .collect(Collectors.toList());
    }
    
    @Override
    public Team find(String teamId) {
        Bson filter = Filters.eq("_id", new ObjectId(teamId));
        return toTeam(teamCollection.find(filter).first());
    }
        

    @Override
    public void add(Team team) {
        teamCollection.insertOne(toDocument(team));
    }

    @Override
    public void delete(String teamId) {
        Bson filter = Filters.eq("_id", new ObjectId(teamId));
        teamCollection.deleteOne(filter);
    }

    @Override
    public void edit(String teamId, Team team) {
        Bson filter = Filters.eq("_id", new ObjectId(teamId));
        Bson newTeam = toDocument(team);
        teamCollection.updateOne(filter, new Document("$set", newTeam));
    }

    private void addAll(List<Team> teams) {
        List<Document> documents = teams.stream().map(p -> toDocument(p)).
                collect(Collectors.toList());
        teamCollection.insertMany(documents);
    }

    /**
     * This method converts MongoDB Documents to Team POJOs, normally we would
     * place this in a DAO
     *
     * @param document
     * @return
     */
    private Team toTeam(Document document) {
        Team team = new Team();
        team.setId(document.get("_id", ObjectId.class).toString());
        team.setCity(document.getString("city"));
        team.setFootballManagerId(document.getInteger("footballManagerId"));
        team.setName(document.getString("name"));
        team.setFootballPlayersId(new TreeSet<>(document.get("footballPlayersId",  ArrayList.class)));
        return team;
    }

    /**
     * This method converts Team POJOs to MongoDB Documents, normally we would
     * place this in a DAO
     *
     * @param team
     * @return
     */
    private Document toDocument(Team team) {
        return new Document()
                .append("city", team.getCity())
                .append("footballManagerId", team.getFootballManagerId())
                .append("name", team.getName())
                .append("footballPlayersId", team.getFootballPlayersId());
    }


}
