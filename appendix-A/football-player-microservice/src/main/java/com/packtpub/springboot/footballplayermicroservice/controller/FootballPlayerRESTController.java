package com.packtpub.springboot.footballplayermicroservice.controller;

import com.packtpub.springboot.footballplayermicroservice.model.FootballPlayer;
import com.packtpub.springboot.footballplayermicroservice.service.FootballPlayerService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Api Controller used to expose our the services of the application.
 *
 * @author Mauro Vocale
 * @version 1.0.0 04/10/2018
 */
@RestController
@RequestMapping("/footballplayer")
public class FootballPlayerRESTController {

    @Autowired
    private FootballPlayerService service;

    @ApiOperation(value = "View all available football players", response
            = Iterable.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully retrieved list"),
        @ApiResponse(code = 404, message
                = "The resource you were trying to reach is not found")
    }
    )
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public Iterable<FootballPlayer> findAll() {
        return service.findAll();
    }

    @ApiOperation(value = "Add a football player")
    @RequestMapping(value = "/save", method = RequestMethod.POST, produces
            = "application/json")
    public FootballPlayer save(@RequestBody FootballPlayer entity) {
        return service.save(entity);
    }

    @ApiOperation(value = "Update a football player")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT, produces
            = "application/json")
    public FootballPlayer edit(@PathVariable Integer id,
            @RequestBody FootballPlayer entity) {
        return service.save(entity);
    }

    @ApiOperation(value = "Delete a football player")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE,
            produces = "application/json")
    public void delete(@PathVariable Integer id) {
        service.deleteById(id);
    }

    @ApiOperation(value = "Search a football player with an ID", response
            = FootballPlayer.class)
    @RequestMapping(value = "/show/{id}", method = RequestMethod.GET, produces
            = "application/json")
    public Optional<FootballPlayer> findById(@PathVariable Integer id) {
        return service.findById(id);
    }
}
