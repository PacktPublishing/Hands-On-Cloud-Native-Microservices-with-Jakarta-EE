package com.packtpub.thorntail.footballplayermicroservice.rest.service;

import com.packtpub.thorntail.footballplayermicroservice.model.FootballPlayer;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Class that exposes the APIs implementations of our CRUD micro service.
 * 
 * @author Mauro Vocale
 * @version 1.0.0 17/08/2018
 */
@ApplicationScoped
@Path("/footballplayer")
public class FootballPlayerFacadeREST extends AbstractFacade<FootballPlayer> {

    @PersistenceContext(unitName = "FootballPlayerPU")
    private EntityManager em;

    public FootballPlayerFacadeREST() {
        super(FootballPlayer.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_JSON})
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void create(FootballPlayer entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void edit(
            @PathParam("id") Integer id, FootballPlayer entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void remove(
            @PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public FootballPlayer find(
            @PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_JSON})
    public List<FootballPlayer> findAll() {
        return super.findAll();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
