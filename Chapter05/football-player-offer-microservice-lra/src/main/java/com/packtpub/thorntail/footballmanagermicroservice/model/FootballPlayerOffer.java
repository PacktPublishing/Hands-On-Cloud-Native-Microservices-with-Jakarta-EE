package com.packtpub.thorntail.footballmanagermicroservice.model;

import java.io.Serializable;
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
 * Domain model class that maps the data stored into football_player_offer table
 * inside database.
 * 
 * @author Mauro Vocale
 * @version 1.0.0 23/08/2018
 */
@Entity
@Table(name = "FOOTBALL_PLAYER_OFFER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FootballPlayerOffer.findAll", query
            = "SELECT f FROM FootballPlayerOffer f")
    })
public class FootballPlayerOffer implements Serializable {

    private static final long serialVersionUID = 7913923259295467044L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_FOOTBALL_PLAYER")
    private Long idFootballPlayer;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_FOOTBALL_MANAGER")
    private Long idFootballManager;
    
    @Column(name = "price")
    private BigInteger price;
    
    @Basic(optional = true)
    @Size(min = 1, max = 50)
    @Column(name = "status")
    private String status;
    
    @Basic(optional = true)
    @Size(min = 1, max = 100)
    @Column(name = "lraId")
    private String lraId;

    public FootballPlayerOffer() {
    }

    public FootballPlayerOffer(Long id, Long idFootballPlayer,
            Long idFootballManager, BigInteger price, String status, String lraId) {
        this.id = id;
        this.idFootballPlayer = idFootballPlayer;
        this.idFootballManager = idFootballManager;
        this.price = price;
        this.status = status;
        this.lraId = lraId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdFootballPlayer() {
        return idFootballPlayer;
    }

    public void setIdFootballPlayer(Long idFootballPlayer) {
        this.idFootballPlayer = idFootballPlayer;
    }

    public Long getIdFootballManager() {
        return idFootballManager;
    }

    public void setIdFootballManager(Long idFootballManager) {
        this.idFootballManager = idFootballManager;
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
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FootballPlayerOffer)) {
            return false;
        }
        FootballPlayerOffer other = (FootballPlayerOffer) object;
        return !((this.id == null && other.getId() != null) ||
                (this.id != null && !this.id.equals(other.getId())));
    }

    @Override
    public String toString() {
        return new StringBuilder().append("FootballPlayerOffer{id=").append(id).
                append(", idFootballPlayer=").append(idFootballPlayer).
                append(", idFootballManager=").append(idFootballManager).
                append(", price=").append(price).append(", status=").
                append(status).append(", lraId=").append(lraId).append("}").
                toString();
    }
}
