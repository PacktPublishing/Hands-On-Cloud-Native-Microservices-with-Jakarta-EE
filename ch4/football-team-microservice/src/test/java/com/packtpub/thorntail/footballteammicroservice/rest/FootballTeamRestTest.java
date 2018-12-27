package com.packtpub.thorntail.footballteammicroservice.rest;

import com.packtpub.thorntail.footballteammicroservice.model.Team;
import com.packtpub.thorntail.footballteammicroservice.service.TeamService;
import com.packtpub.thorntail.footballteammicroservice.utils.Producers;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static org.hamcrest.CoreMatchers.is;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.wildfly.swarm.jaxrs.JAXRSArchive;

/**
 * Unit Test class needed to test the APIs.
 *
 * @author Mauro Vocale
 * @version 1.0.0 22/07/2018
 */
@RunWith(Arquillian.class)
public class FootballTeamRestTest {
    
    private static final String API_URL = "http://localhost:8080/footballteam";
    
    private static String teamId = null;

    /**
     *
     * @return @throws Exception
     */
    @Deployment
    public static Archive createDeployment() throws Exception {
        JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class);
        deployment.addPackage(Team.class.getPackage());
        deployment.addPackage(RestApplication.class.getPackage());
        deployment.addPackage(TeamService.class.getPackage());
        deployment.addPackage(Producers.class.getPackage());

        deployment.addAsWebInfResource(new ClassLoaderAsset(
                "META-INF/beans.xml", FootballTeamRest.class.
                        getClassLoader()),
                "classes/META-INF/beans.xml");

        deployment.addAllDependencies();
        System.out.println(deployment.toString(true));
        
        return deployment;
    }

    private final Client client = ClientBuilder.newBuilder().build();

    private WebTarget target;

    public FootballTeamRestTest() {
    }

    @Before
    public void setUp() {
        target = client.target(API_URL);
    }

    /**
     * Test of create method, of class FootballTeamRest.
     */
    @Test
    @InSequence(1)
    public void testCreate() {
        System.out.println("create");
        Team team = new Team("Test_NewTeam", "Favignana",
                3, new HashSet<>(Arrays.asList(3, 5, 6, 7, 10,
                        12, 14, 16, 18, 19, 20)));
        Response response = target.request().post(Entity.entity(team,
                MediaType.APPLICATION_JSON_TYPE));
        assertThat(response.getStatus(), is(Response.Status.NO_CONTENT.
                getStatusCode()));
    }

    /**
     * Test of edit method, of class FootballManagerFacadeREST.
     */
    @Test
    @InSequence(3)
    public void testEdit() {
        System.out.println("edit");
        Team team = target.path(teamId).request().get(
                new GenericTypeTeamImpl());
        team.setName("New_Winner_Team");
        Response response = target.path(teamId).request().put(Entity.
                entity(team,
                        MediaType.APPLICATION_JSON_TYPE));
        assertThat(response.getStatus(), is(Response.Status.NO_CONTENT.
                getStatusCode()));
    }

    /**
     * Test of remove method, of class FootballManagerFacadeREST.
     */
    @Test
    @InSequence(5)
    public void testRemove() {
        System.out.println("remove");
        Response response = target.path(teamId).request().delete();
        assertThat(response.getStatus(), is(Response.Status.NO_CONTENT.
                getStatusCode()));
    }

    /**
     * Test of find method, of class FootballManagerFacadeREST.
     */
    @Test
    @InSequence(4)
    public void testFind() {
        System.out.println("find");
        Response response = target.path(teamId).request().get();
        assertThat(response.getStatus(), is(Response.Status.OK.getStatusCode()));
        Team responseEntity
                = response.readEntity(new GenericTypeTeamImpl());
        assertThat(responseEntity.getName(), is("New_Winner_Team"));
        assertThat(responseEntity.getCity(), is("Favignana"));
        assertThat(responseEntity.getFootballManagerId(), is(3));
    }

    /**
     * Test of findAll method, of class FootballManagerFacadeREST.
     */
    @Test
    @InSequence(2)
    public void testFindAll() {
        System.out.println("findAll");
        Response response = target.request().get();
        assertThat(response.getStatus(), is(Response.Status.OK.getStatusCode()));
        List<Team> responseEntity
                = response.readEntity(new GenericTypeTeamListImpl());
        assertThat(responseEntity.size(), is(3));
        Team newTeam = responseEntity.get(2);
        teamId = newTeam.getId();
    }

    private static class GenericTypeTeamImpl extends GenericType<Team> {

        GenericTypeTeamImpl() {
        }
    }

    private static class GenericTypeTeamListImpl extends GenericType<List<Team>> {

        GenericTypeTeamListImpl() {
        }
    }
    
}
