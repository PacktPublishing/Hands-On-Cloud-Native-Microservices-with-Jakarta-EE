package com.footballplayer.market.client;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.plugins.providers.jackson.ResteasyJackson2Provider;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

/**
 * Client class that simulate the scenario of football player market. The simple
 * scenario is: a football manager performs an offer related to a particular
 * football player. If the offer's price is less than the 80% of the value of
 * the football player the offer will be rejected and the LRA will performs a
 * compensate action. Otherwise the offer will be accepted and the LRA
 * coordinator will perform a complete action.
 *
 * @author Mauro Vocale
 * @version 1.0.0 23/08/2018
 */
public class FootballPlayerMarketClient {

    /**
     * Static method used to register the Jackson provider needed by RestEasy to
     * marshall / unmarshall the objects.
     */
    static {
        ResteasyProviderFactory providerFactory = ResteasyProviderFactory.
                getInstance();
        // add the providers that are needed in the client. In this case adding the JSON provider
        providerFactory.registerProvider(ResteasyJackson2Provider.class);
        RegisterBuiltin.register(providerFactory);
    }

    public static void main(String[] args) throws MalformedURLException {
        // Create the JAX-RS client
        Client footballPlayerClient = ClientBuilder.newClient();
        WebTarget footballPlayerTarget = footballPlayerClient.target(new URL(
                "http://localhost:8480/footballplayer-market").
                toExternalForm());

        // Build the offer: using the parameter otherwise I set the 22 millions as
        // default value.
        BigInteger priceParameter = args.length > 0 && args[0] != null
                ? new BigInteger(args[0])
                : new BigInteger("22");
        
        String idFootballPlayer = args.length > 0 && args[1] != null
                ? args[1]
                : "1";
        
        FootballPlayerOffer offer = new FootballPlayerOffer(Long.valueOf(idFootballPlayer),
                Long.valueOf("1"), priceParameter, null, null);

        // Send the offer
        Response response = footballPlayerTarget.request().post(Entity.entity(
                offer,
                MediaType.APPLICATION_JSON_TYPE));

        // Check the response status: if is different from Response.Status.NO_CONTENT
        // I will throw an exception.
        if (response.getStatus() != Response.Status.NO_CONTENT.getStatusCode()) {
            throw new RuntimeException("Failed to perform football player offer");
        }
    }

}
