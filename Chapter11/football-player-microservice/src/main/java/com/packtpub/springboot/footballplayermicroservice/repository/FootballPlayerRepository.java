package com.packtpub.springboot.footballplayermicroservice.repository;

import com.packtpub.springboot.footballplayermicroservice.model.FootballPlayer;
import org.springframework.data.repository.CrudRepository;

/**
 * FootballPlayerRepository extends the CrudRepository interface. The type of entity 
 * and ID that it works with, FootballPlayer and Integer, are specified in the generic parameters 
 * on CrudRepository. 
 * By extending CrudRepository, FootballPlayerRepository inherits several methods for working with 
 * FootballPlayer persistence, including methods for saving, deleting, and finding FootballPlayer entities.
 * 
 * @author Mauro Vocale
 * @version 30/09/2018
 */
public interface FootballPlayerRepository extends CrudRepository<FootballPlayer, Integer> {
    
}
