package com.packtpub.vertx.footballplayermicroservice.model;

import io.vertx.core.json.JsonObject;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * Data Object that contains the value of the data stored into the database.
 *
 * @author Mauro Vocale
 * @version 1.0.0 11/10/2018
 *
 */
public class FootballPlayer implements Serializable {

    private static final long serialVersionUID = -92346781936044228L;

    private Integer id;

    private String name;

    private String surname;

    private int age;

    private String team;

    private String position;

    private BigInteger price;

    public FootballPlayer() {
    }

    public FootballPlayer(Integer id, String name, String surname, int age,
            String team, String position, BigInteger price) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.team = team;
        this.position = position;
        this.price = price;
    }
    
    public FootballPlayer(JsonObject json) {
        this(
            json.getInteger("id", -1),
            json.getString("name"),
            json.getString("surname"),
            json.getInteger("age", -1),
            json.getString("team"),
            json.getString("position"),
            BigInteger.valueOf(json.getInteger("price"))
        );
    }


    public void setId(Integer id) {
        this.id = id;
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
