/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.packtpub.springboot.footballplayermicroservice.service;

import com.packtpub.springboot.footballplayermicroservice.model.FootballPlayer;
import com.packtpub.springboot.footballplayermicroservice.repository.FootballPlayerRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class used to decouple the business logic between the REST API Controller
 * and the data access layer.
 * 
 * @author Mauro Vocale
 * @version 1.0.0 04/10/2018
 */
@Service
public class FootballPlayerService {
    
    @Autowired
    private FootballPlayerRepository repository;
    
    public Iterable<FootballPlayer> findAll() {
        return repository.findAll();
    }
    
    public FootballPlayer save(FootballPlayer entity) {
        return repository.save(entity);
    }
    
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
    
    public Optional<FootballPlayer> findById(Integer id) {
        return repository.findById(id);
    }
    
}
