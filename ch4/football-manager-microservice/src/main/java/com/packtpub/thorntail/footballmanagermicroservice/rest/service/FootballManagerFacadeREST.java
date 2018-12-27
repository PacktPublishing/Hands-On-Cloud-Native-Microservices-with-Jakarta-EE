package com.packtpub.thorntail.footballmanagermicroservice.rest.service;

import com.packtpub.thorntail.footballmanagermicroservice.model.FootballManager;
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
 * Class that exposes the APIs implementations of our CRUD microservice.
 *
 * @author Mauro Vocale
 * @version 1.0.0 19/08/2018
 */
@ApplicationScoped
@Path("/footballmanager")
public class FootballManagerFacadeREST extends AbstractFacade<FootballManager> {

    @PersistenceContext(unitName = "FootballManagerPU")
    private EntityManager em;

    public FootballManagerFacadeREST() {
        super(FootballManager.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_JSON})
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void create(FootballManager entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void edit(
            @PathParam("id") Long id, FootballManager entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void remove(
            @PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public FootballManager find(
            @PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_JSON})
    public List<FootballManager> findAll() {
        return super.findAll();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
