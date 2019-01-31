package com.packtpub.thorntail.footballmanagermicroservice.rest.service;

import com.packtpub.thorntail.footballmanagermicroservice.model.FootballPlayerOffer;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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
import org.eclipse.microprofile.lra.annotation.LRA;

/**
 * Class that exposes the APIs implementations of our CRUD microservice.
 *
 * @author Mauro Vocale
 * @version 1.0.0 23/08/2018
 */
@ApplicationScoped
@Path("/footballplayeroffer")
public class FootballPlayerOfferFacadeREST extends AbstractFacade<FootballPlayerOffer> {

    @PersistenceContext(unitName = "FootballPlayerOfferPU")
    private EntityManager em;

    public FootballPlayerOfferFacadeREST() {
        super(FootballPlayerOffer.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_JSON})
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void create(FootballPlayerOffer entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    @LRA(value = LRA.Type.SUPPORTS)
    public void edit(
            @PathParam("id") Long id, FootballPlayerOffer entity) {
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
    public FootballPlayerOffer find(
            @PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Path("lraId/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public FootballPlayerOffer findByLraId(
            @PathParam("id") String lraId) {
        TypedQuery<FootballPlayerOffer> query = getEntityManager().createQuery(
                "SELECT f FROM FootballPlayerOffer f WHERE f.lraId = :lraId",
                FootballPlayerOffer.class);
        return query.setParameter("lraId", lraId).getSingleResult();
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_JSON})
    public List<FootballPlayerOffer> findAll() {
        return super.findAll();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
