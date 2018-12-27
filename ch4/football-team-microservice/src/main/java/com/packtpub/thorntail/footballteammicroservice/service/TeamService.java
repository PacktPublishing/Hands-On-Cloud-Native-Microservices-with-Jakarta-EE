package com.packtpub.thorntail.footballteammicroservice.service;

import com.packtpub.thorntail.footballteammicroservice.model.Team;
import java.util.List;

/**
 * Interface to map the CRUD operation on MongoDB collection.
 * 
 * @author Mauro Vocale
 * @version 1.0.0 22/07/2018
 */
public interface TeamService {
    
    List<Team> getTeams();
	
    void add(Team team);
	
    void delete(String teamId);
    
    void edit(String teamId, Team team);
    
    Team find (String teamId);
    
}
