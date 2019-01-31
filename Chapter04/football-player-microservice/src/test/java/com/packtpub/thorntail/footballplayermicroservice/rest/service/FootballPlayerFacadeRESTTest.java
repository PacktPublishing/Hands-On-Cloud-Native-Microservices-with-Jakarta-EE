package com.packtpub.thorntail.footballplayermicroservice.rest.service;

import com.packtpub.thorntail.footballplayermicroservice.model.FootballPlayer;
import com.packtpub.thorntail.footballplayermicroservice.rest.RestApplication;
import java.math.BigInteger;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static org.hamcrest.CoreMatchers.*;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wildfly.swarm.jaxrs.JAXRSArchive;

/**
 * Unit Test class needed to test the APIs.
 * 
 * @author Mauro Vocale
 * @version 1.0.0 18/07/2018
 */
@RunWith(Arquillian.class)
public class FootballPlayerFacadeRESTTest {

    private static final String API_URL = "http://localhost:8080/footballplayer";
    
    /**
     *
     * @return @throws Exception
     */
    @Deployment
    public static Archive createDeployment() throws Exception {
        JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class);
        deployment.addPackage(FootballPlayer.class.getPackage());
        deployment.addPackage(AbstractFacade.class.getPackage());
        deployment.addPackage(RestApplication.class.getPackage());

        deployment.addAsWebInfResource(new ClassLoaderAsset(
                "META-INF/create.sql", FootballPlayerFacadeREST.class.
                        getClassLoader()),
                "classes/META-INF/create.sql");

        deployment.addAsWebInfResource(new ClassLoaderAsset(
                "META-INF/load.sql", FootballPlayerFacadeREST.class.
                        getClassLoader()),
                "classes/META-INF/load.sql");

        deployment.addAsWebInfResource(new ClassLoaderAsset(
                "META-INF/persistence.xml", FootballPlayerFacadeREST.class.
                        getClassLoader()),
                "classes/META-INF/persistence.xml");

        deployment.addAsWebInfResource(new ClassLoaderAsset(
                "project-defaults.yml", FootballPlayerFacadeREST.class.
                        getClassLoader()),
                "classes/project-defaults.yml");

        deployment.addAllDependencies();
        System.out.println(deployment.toString(true));
        return deployment;
    }

    private final Client client = ClientBuilder.newBuilder().build();

    private WebTarget target;

    public FootballPlayerFacadeRESTTest() {
    }

    @Before
    public void setUp() {
        target = client.target(API_URL);
    }


    /**
     * Test of create method, of class FootballPlayerFacadeREST.
     */
    @Test
    @InSequence(2)
    public void testCreate() {
        System.out.println("create");
        FootballPlayer player = new FootballPlayer("Mauro", "Vocale", 38,
                "Juventus",
                "central midfielder", new BigInteger("100"));
        Response response = target.request().post(Entity.entity(player,
                MediaType.APPLICATION_JSON_TYPE));
        assertThat(response.getStatus(), is(Response.Status.NO_CONTENT.
                getStatusCode()));
    }

    /**
     * Test of edit method, of class FootballPlayerFacadeREST.
     */
    @Test
    @InSequence(3)
    public void testEdit() {
        System.out.println("edit");
        FootballPlayer player = target.path("24").request().get(
                new GenericTypeFootballPlayerImpl());
        player.setPrice(new BigInteger("150"));
        Response response = target.path("24").request().put(Entity.
                entity(player,
                        MediaType.APPLICATION_JSON_TYPE));
        assertThat(response.getStatus(), is(Response.Status.NO_CONTENT.
                getStatusCode()));
    }

    /**
     * Test of remove method, of class FootballPlayerFacadeREST.
     */
    @Test
    @InSequence(5)
    public void testRemove() {
        System.out.println("remove");
        Response response = target.path("24").request().delete();
        assertThat(response.getStatus(), is(Response.Status.NO_CONTENT.
                getStatusCode()));
    }

    /**
     * Test of find method, of class FootballPlayerFacadeREST.
     */
    @Test
    @InSequence(4)
    public void testFind() {
        System.out.println("find");
        Response response = target.path("24").request().get();
        assertThat(response.getStatus(), is(Response.Status.OK.getStatusCode()));
        FootballPlayer responseEntity
                = response.readEntity(new GenericTypeFootballPlayerImpl());
        assertThat(responseEntity.getName(), is("Mauro"));
        assertThat(responseEntity.getSurname(), is("Vocale"));
        assertThat(responseEntity.getPrice(), is(new BigInteger("150")));
    }

    /**
     * Test of findAll method, of class FootballPlayerFacadeREST.
     */
    @Test
    @InSequence(1)
    public void testFindAll() {
        System.out.println("findAll");
        Response response = target.request().get();
        assertThat(response.getStatus(), is(Response.Status.OK.getStatusCode()));
        List<FootballPlayer> responseEntity
                = response.readEntity(new GenericTypeFootballPlayerListImpl());
        assertThat(responseEntity.get(0).getName(), is("Gianluigi"));

    }

    private static class GenericTypeFootballPlayerImpl extends GenericType<FootballPlayer> {

        GenericTypeFootballPlayerImpl() {
        }
    }

    private static class GenericTypeFootballPlayerListImpl extends GenericType<List<FootballPlayer>> {

        GenericTypeFootballPlayerListImpl() {
        }
    }

}
