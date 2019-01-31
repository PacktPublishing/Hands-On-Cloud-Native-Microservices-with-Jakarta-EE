package com.packtpub.thorntail.footballmanagermicroservice.model;

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
 * Domain model class that maps the data stored into football_manager table
 * inside database.
 * 
 * @author Mauro Vocale
 * @version 1.0.0 19/08/2018
 */
@Entity
@Table(name = "FOOTBALL_MANAGER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FootballManager.findAll", query
            = "SELECT f FROM FootballManager f")
    })
public class FootballManager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "NAME")
    private String name;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "SURNAME")
    private String surname;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "AGE")
    private int age;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "NICKNAME")
    private String nickname;

    public FootballManager() {
    }

    public FootballManager(String name, String surname, int age,
            String nickname) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.nickname = nickname;
    }

    public Long getId() {
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FootballManager)) {
            return false;
        }
        FootballManager other = (FootballManager) object;
        return !((this.id == null && other.getId() != null) ||
                (this.id != null && !this.id.equals(other.getId())));
    }

    @Override
    public String toString() {
        return new StringBuilder().append("FootballManager{id=").append(id).
                append(", name=").append(name).append(", surname=").
                append(surname).append(", age=").append(age).
                append(", nickname=").append(nickname).append("}").toString();
    }

    
    
}
