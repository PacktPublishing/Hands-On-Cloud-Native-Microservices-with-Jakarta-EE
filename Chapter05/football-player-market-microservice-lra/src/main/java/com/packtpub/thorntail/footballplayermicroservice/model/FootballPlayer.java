package com.packtpub.thorntail.footballplayermicroservice.model;

import java.io.Serializable;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Simple POJO that maps the fields related to the Domain model class 
 * related to the football_player micro service.
 * 
 * @author Mauro Vocale
 * @version 1.0.0 21/08/2018
 */
@XmlRootElement
public class FootballPlayer implements Serializable {

    private static final long serialVersionUID = -92346781936044228L;
    
    private Integer id;
    
    private String name;
    
    private String surname;
    
    private int age;
    
    private String team;
    
    private String position;
    
    private BigInteger price;
    
    private String status;
    
    private String lraId;
    
    public FootballPlayer() {
    }

    public FootballPlayer(String name, String surname, int age,
            String team, String position, BigInteger price, String status, String lraId) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.team = team;
        this.position = position;
        this.price = price;
        this.status = status;
        this.lraId = lraId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLraId() {
        return lraId;
    }

    public void setLraId(String lraId) {
        this.lraId = lraId;
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
                append(", price=").append(price).append(", status=").
                append(status).append(", lraId=").append(lraId).append("}").
                toString();
    }  

}
