package com.packtpub.thorntail.footballteammicroservice.rest;

import com.packtpub.thorntail.footballteammicroservice.model.Team;
import com.packtpub.thorntail.footballteammicroservice.service.TeamService;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
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
 * @version 1.0.0 22/08/2018
 */
@ApplicationScoped
@Path("/footballteam")
public class FootballTeamRest {
    
    @Inject
    private TeamService teamService;
    
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void create(Team team) {
        teamService.add(team);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void edit(
            @PathParam("id") String id, Team team) {
        teamService.edit(id, team);
    }

    @DELETE
    @Path("{id}")
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void remove(
            @PathParam("id") String id) {
        teamService.delete(id);
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Team find(
            @PathParam("id") String id) {
        return teamService.find(id);
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Team> findAll() {
        return teamService.getTeams();
    }
    
}
