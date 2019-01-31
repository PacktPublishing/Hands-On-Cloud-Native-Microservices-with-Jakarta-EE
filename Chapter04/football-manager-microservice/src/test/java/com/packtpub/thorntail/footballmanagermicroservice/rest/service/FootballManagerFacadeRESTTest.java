package com.packtpub.thorntail.footballmanagermicroservice.rest.service;

import com.packtpub.thorntail.footballmanagermicroservice.model.FootballManager;
import com.packtpub.thorntail.footballmanagermicroservice.rest.RestApplication;
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
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wildfly.swarm.jaxrs.JAXRSArchive;

/**
 * Unit Test class needed to test the APIs.
 *
 * @author Mauro Vocale
 * @version 1.0.0 19/07/2018
 */
@RunWith(Arquillian.class)
public class FootballManagerFacadeRESTTest {

    private static final String API_URL = "http://localhost:8080/footballmanager";

    /**
     *
     * @return @throws Exception
     */
    @Deployment
    public static Archive createDeployment() throws Exception {
        JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class);
        deployment.addPackage(FootballManager.class.getPackage());
        deployment.addPackage(AbstractFacade.class.getPackage());
        deployment.addPackage(RestApplication.class.getPackage());

        deployment.addAsWebInfResource(new ClassLoaderAsset(
                "META-INF/create.sql", FootballManagerFacadeREST.class.
                        getClassLoader()),
                "classes/META-INF/create.sql");

        deployment.addAsWebInfResource(new ClassLoaderAsset(
                "META-INF/load.sql", FootballManagerFacadeREST.class.
                        getClassLoader()),
                "classes/META-INF/load.sql");

        deployment.addAsWebInfResource(new ClassLoaderAsset(
                "META-INF/persistence.xml", FootballManagerFacadeREST.class.
                        getClassLoader()),
                "classes/META-INF/persistence.xml");

        deployment.addAsWebInfResource(new ClassLoaderAsset(
                "project-defaults.yml", FootballManagerFacadeREST.class.
                        getClassLoader()),
                "classes/project-defaults.yml");

        deployment.addAllDependencies();
        System.out.println(deployment.toString(true));
        
        return deployment;
    }

    private final Client client = ClientBuilder.newBuilder().build();

    private WebTarget target;

    public FootballManagerFacadeRESTTest() {
    }

    @Before
    public void setUp() {
        target = client.target(API_URL);
    }

    /**
     * Test of create method, of class FootballManagerFacadeREST.
     */
    @Test
    @InSequence(2)
    public void testCreate() {
        System.out.println("create");
        FootballManager manager = new FootballManager("Massimiliano", "Allegri", 50,
                "Max_7Win");
        Response response = target.request().post(Entity.entity(manager,
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
        FootballManager manager = target.path("6").request().get(
                new GenericTypeFootballManagerImpl());
        manager.setNickname("Max_8Win");
        Response response = target.path("6").request().put(Entity.
                entity(manager,
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
        Response response = target.path("6").request().delete();
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
        Response response = target.path("6").request().get();
        assertThat(response.getStatus(), is(Response.Status.OK.getStatusCode()));
        FootballManager responseEntity
                = response.readEntity(new GenericTypeFootballManagerImpl());
        assertThat(responseEntity.getName(), is("Massimiliano"));
        assertThat(responseEntity.getSurname(), is("Allegri"));
        assertThat(responseEntity.getNickname(), is("Max_8Win"));
    }

    /**
     * Test of findAll method, of class FootballManagerFacadeREST.
     */
    @Test
    @InSequence(1)
    public void testFindAll() {
        System.out.println("findAll");
        Response response = target.request().get();
        assertThat(response.getStatus(), is(Response.Status.OK.getStatusCode()));
        List<FootballManager> responseEntity
                = response.readEntity(new GenericTypeFootballManagerListImpl());
        assertThat(responseEntity.get(0).getName(), is("Mauro"));

    }

    private static class GenericTypeFootballManagerImpl extends GenericType<FootballManager> {

        GenericTypeFootballManagerImpl() {
        }
    }

    private static class GenericTypeFootballManagerListImpl extends GenericType<List<FootballManager>> {

        GenericTypeFootballManagerListImpl() {
        }
    }

}
