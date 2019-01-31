package com.packtpub.thorntail.footballplayermicroservice.model;

import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Domain model class that maps the data stored into football_player table
 * inside database.
 * 
 * @author Mauro Vocale
 * @version 1.0.0 15/08/2018
 */
@Entity
@Table(name = "football_player")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FootballPlayer.findAll", query
            = "SELECT f FROM FootballPlayer f")
    })
public class FootballPlayer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "name")
    private String name;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "surname")
    private String surname;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "age")
    private int age;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "team")
    private String team;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "position")
    private String position;
    
    @Column(name = "price")
    private BigInteger price;

    public FootballPlayer() {
    }

    public FootballPlayer(String name, String surname, int age,
            String team, String position, BigInteger price) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.team = team;
        this.position = position;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public BigInteger getPrice() {
        return price;
    }

    public void setPrice(BigInteger price) {
        this.price = price;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof FootballPlayer)) {
            return false;
        }
        FootballPlayer other = (FootballPlayer) object;
        return !((this.id == null && other.getId() != null) || (this.id != null
                && !this.id.equals(other.getId())));
    }

    @Override
    public String toString() {
        return new StringBuilder().append("FootballPlayer{id=").append(id).
                append(", name=").append(name).append(", surname=").
                append(surname).append(", age=").append(age).append(", team=").
                append(team).append(", position=").append(position).
                append(", price=").append(price).append("}").toString();
    }

}
