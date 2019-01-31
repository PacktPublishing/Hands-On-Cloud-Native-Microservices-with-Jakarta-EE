package com.packtpub.thorntail.footballplayermicroservice.rest.service;

import com.packtpub.thorntail.footballplayermicroservice.model.FootballPlayer;
import com.packtpub.thorntail.footballplayermicroservice.model.FootballPlayerOffer;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.lra.annotation.Compensate;
import org.eclipse.microprofile.lra.annotation.Complete;
import org.eclipse.microprofile.lra.annotation.LRA;
import org.eclipse.microprofile.lra.client.LRAClient;

/**
 * Class that simulates a coarse grained API gateway that implements the
 * workflow invoking the football-player-microservice
 *
 * @author Mauro Vocale
 * @version 1.0.0 21/08/2018
 */
@ApplicationScoped
@Path("/footballplayer-market")
public class FootballPlayerMarketREST {

    private static final Logger LOG
            = Logger.getLogger(FootballPlayerMarketREST.class.getName());

    private Client footballPlayerClient;

    private Client footballPlayerOfferClient;

    private WebTarget footballPlayerTarget;

    private WebTarget footballPlayerOfferTarget;

    @Inject
    private LRAClient lraClient;

    @PostConstruct
    private void init() {
        footballPlayerClient = ClientBuilder.newClient();
        footballPlayerOfferClient = ClientBuilder.newClient();
        footballPlayerTarget = footballPlayerClient.target(
                "http://localhost:8080/footballplayer");
        footballPlayerOfferTarget = footballPlayerOfferClient.target(
                "http://localhost:8680/footballplayeroffer");
    }

    @PreDestroy
    private void destroy() {
        footballPlayerClient.close();
        footballPlayerOfferClient.close();
    }
    
    /**
     * Business method that performs the send offer business logic.
     * The method implement the business logic related to create the football
     * player offer domain record and sets the status to a non final status, "SEND".
     * It also set the status of the football player to Reserved.
     * The LRA coordinator, based on the Response.Status, will decide how to
     * complete or compensate the business logic setting the definitive status.
     * 
     * @param footballPlayerOffer The POJO that maps the football player offer
     * data.
     */
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @LRA(value = LRA.Type.REQUIRED,
            cancelOn = {Response.Status.INTERNAL_SERVER_ERROR}, // cancel on a 500 code
            cancelOnFamily = {Response.Status.Family.CLIENT_ERROR} // cancel on any 4xx code
    )
    public void sendOffer(FootballPlayerOffer footballPlayerOffer) {
        LOG.log(Level.INFO, "Start method sendOffer");

        LOG.log(Level.FINE, "Retrieving football player with id {0}",
                footballPlayerOffer.getIdFootballPlayer());

        String lraIdUrl = lraClient.getCurrent().toString();
        String lraId = lraIdUrl.substring(lraIdUrl.lastIndexOf('/') + 1);

        LOG.log(Level.FINE, "Value of LRA_ID {0}", lraId);

        // Create the offer
        LOG.log(Level.FINE, "Creating offer ...");
        footballPlayerOffer.setStatus("SEND");
        footballPlayerOffer.setLraId(lraId);
        Response response
                = footballPlayerOfferTarget.request().post(Entity.entity(
                        footballPlayerOffer,
                        MediaType.APPLICATION_JSON_TYPE));

        LOG.log(Level.FINE, "Offer created with response code {0}", response.
                getStatus());

        if (response.getStatus() == Response.Status.NO_CONTENT.getStatusCode()) {

            FootballPlayer player = footballPlayerTarget.path(
                    footballPlayerOffer.
                            getIdFootballPlayer().toString()).
                    request().get(new GenericTypeFootballPlayerImpl());

            LOG.log(Level.FINE, "Got football player {0}", player);

            player.setStatus("Reserved");
            player.setLraId(lraId);

            LOG.log(Level.FINE, "Changing football player status ...");

            footballPlayerTarget.path(footballPlayerOffer.
                    getIdFootballPlayer().toString()).request().put(Entity.
                            entity(player,
                                    MediaType.APPLICATION_JSON_TYPE));

            // Check about the price of the offer: if it is less than 80% of the
            // value of the football player I will refuse the offer
            BigInteger price = footballPlayerOffer.getPrice();
            
            LOG.log(Level.FINE, "Value of offer price {0}", price);
            LOG.log(Level.FINE, "Value of football player price {0}", player.getPrice());
            
            if ((price.multiply(new BigInteger("100")).divide(player.getPrice())).
                    intValue() < 80) {
                throw new WebApplicationException("The offer is unacceptable!",
                        Response.Status.INTERNAL_SERVER_ERROR);
            }

        } else {
            throw new WebApplicationException(
                    Response.Status.INTERNAL_SERVER_ERROR);
        }

        LOG.log(Level.INFO, "End method sendOffer");

    }
    
    /**
     * LRA complete method: it sets the final status of the football player offer
     * and football player based on a successful response of the send offer
     * method.
     * 
     * @param lraId The Long Running Action identifier needed to retrieve
     * the record on which perform the operation.
     */
    @PUT
    @Path("/complete")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Complete
    public void confirmOffer(
            @HeaderParam(LRAClient.LRA_HTTP_HEADER) String lraId) {
        LOG.log(Level.INFO,
                "Start method confirmOffer: I'm in LRA complete phase");

        LOG.log(Level.FINE, "Value of header lraId {0}", lraId);

        String lraIdParameter = lraId.substring(lraId.lastIndexOf('/') + 1);

        LOG.log(Level.FINE, "Value of lraIdParameter {0}", lraIdParameter);

        // Set the offer to accepted
        LOG.log(Level.FINE, "Setting the offer as ACCEPTED ...");
        FootballPlayerOffer fpo
                = footballPlayerOfferTarget.path("lraId/"
                        + lraIdParameter).request().get(
                                new GenericTypeFootballPlayerOfferImpl());

        fpo.setStatus("ACCEPTED");

        footballPlayerOfferTarget.path(fpo.getId().toString()).request().put(
                Entity.
                        entity(fpo,
                                MediaType.APPLICATION_JSON_TYPE));
        
        LOG.log(Level.FINE, "Set the offer as ACCEPTED ...");

        // Set the football player status to purchased
        FootballPlayer player = footballPlayerTarget.path("lraId/"
                + lraIdParameter).
                request().get(new GenericTypeFootballPlayerImpl());

        LOG.log(Level.FINE, "Got football player {0}", player);

        player.setStatus("Purchased");
        player.setLraId(null);

        LOG.log(Level.FINE, "Changing football player status ...");

        footballPlayerTarget.path(player.getId().toString()).request().put(
                Entity.
                        entity(player,
                                MediaType.APPLICATION_JSON_TYPE));

        LOG.log(Level.INFO,
                "End method confirmOffer: LRA complete phase terminated");
    }
    
    /**
     * LRA compensate method: it sets the final status of the football player offer
     * and football player based on a failed response of the send offer
     * method.
     * 
     * @param lraId The Long Running Action identifier needed to retrieve
     * the record on which perform the operation.
     * @return the Response of the operation.
     */
    @PUT
    @Path("/compensate")
    @Produces(MediaType.APPLICATION_JSON)
    @Compensate
    public Response compensateWork(
            @HeaderParam(LRAClient.LRA_HTTP_HEADER) String lraId) {

        LOG.log(Level.INFO,
                "Start method compensateWork: I'm in LRA compensate phase");

        String lraIdParameter = lraId.substring(lraId.lastIndexOf('/') + 1);

        LOG.log(Level.FINE, "Value of lraIdParameter {0}", lraIdParameter);
        
        LOG.log(Level.FINE, "Setting the offer as REFUSED ...");
        // Set the offer to REFUSED
        FootballPlayerOffer fpo
                = footballPlayerOfferTarget.path("lraId/"
                        + lraIdParameter).request().get(
                                new GenericTypeFootballPlayerOfferImpl());

        fpo.setStatus("REFUSED");

        footballPlayerOfferTarget.path(fpo.getId().toString()).request().put(
                Entity.
                        entity(fpo,
                                MediaType.APPLICATION_JSON_TYPE));
        
        LOG.log(Level.FINE, "Set the offer as REFUSED ...");

        FootballPlayer player = footballPlayerTarget.path("lraId/"
                + lraIdParameter).
                request().get(new GenericTypeFootballPlayerImpl());

        LOG.log(Level.FINE, "Got football player {0}", player);

        player.setStatus("Free");
        player.setLraId(null);

        LOG.log(Level.FINE, "Changing football player status ...");

        footballPlayerTarget.path(player.getId().toString()).request().put(
                Entity.
                        entity(player,
                                MediaType.APPLICATION_JSON_TYPE));

        LOG.log(Level.INFO,
                "End method compensateWork: LRA compensate phase terminated");

        return Response.ok().build();
    }

    private static class GenericTypeFootballPlayerImpl extends GenericType<FootballPlayer> {

        GenericTypeFootballPlayerImpl() {
        }
    }

    private static class GenericTypeFootballPlayerOfferImpl extends GenericType<FootballPlayerOffer> {

        GenericTypeFootballPlayerOfferImpl() {
        }
    }

}
