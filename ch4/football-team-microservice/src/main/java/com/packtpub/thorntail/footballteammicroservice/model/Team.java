
package com.packtpub.thorntail.footballteammicroservice.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * Pojo object that maps the MongoDB team collection.
 * 
 * @author Mauro Vocale
 * @version 1.0.0 22/07/2018
 */
public class Team implements Serializable {
    
    private static final long serialVersionUID = -7085788483700130007L;
    
    private String id;
    
    private String name;
    
    private String city;
    
    private Integer footballManagerId;
    
    private Set<Integer> footballPlayersId;

    public Team() {
    }

    public Team(String name, String city, Integer footballManagerId,
            Set<Integer> footballPlayersId) {
        this.name = name;
        this.city = city;
        this.footballManagerId = footballManagerId;
        this.footballPlayersId = footballPlayersId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getFootballManagerId() {
        return footballManagerId;
    }

    public void setFootballManagerId(Integer footballManagerId) {
        this.footballManagerId = footballManagerId;
    }

    public Set<Integer> getFootballPlayersId() {
        return footballPlayersId;
    }

    public void setFootballPlayersId(Set<Integer> footballPlayersId) {
        this.footballPlayersId = footballPlayersId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Team other = (Team) obj;
        return Objects.equals(this.id, other.getId());
    }

    @Override
    public String toString() {
        return new StringBuilder().append("Team{id=").append(id).
                append(", name=").append(name).append(", city=").append(city).
                append(", footballManagerId=").append(footballManagerId).
                append(", footballPlayersId=").append(footballPlayersId).
                append("}").toString();
    }
    
    
    
}
