package com.packtpub.thorntail.footballplayermicroservice.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Objects;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Simple POJO class that represents an offer for a football player.
 * 
 * @author Mauro Vocale
 * @version 1.0.0 23/08/2018
 */
@XmlRootElement
public class FootballPlayerOffer implements Serializable {

    private static final long serialVersionUID = -4600821893810282984L;
    
    // Primary key of the record
    private Long id;
    
    // Id of the football player
    private Long idFootballPlayer;

    // Id of the football manager that performs the offer
    private Long idFootballManager;

    // The value of the offer
    private BigInteger price;

    // The status of the offer
    private String status;

    // The Long Running Action id
    private String lraId;

    public FootballPlayerOffer() {
    }

    public FootballPlayerOffer(Long id, Long idFootballPlayer,
            Long idFootballManager, BigInteger price, String status,
            String lraId) {
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
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.id);
        hash = 71 * hash + Objects.hashCode(this.idFootballPlayer);
        hash = 71 * hash + Objects.hashCode(this.idFootballManager);
        hash = 71 * hash + Objects.hashCode(this.price);
        hash = 71 * hash + Objects.hashCode(this.status);
        hash = 71 * hash + Objects.hashCode(this.lraId);
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
        final FootballPlayerOffer other = (FootballPlayerOffer) obj;
        if (!Objects.equals(this.status, other.getStatus())) {
            return false;
        }
        if (!Objects.equals(this.lraId, other.getLraId())) {
            return false;
        }
        if (!Objects.equals(this.id, other.getId())) {
            return false;
        }
        if (!Objects.equals(this.idFootballPlayer, other.getIdFootballPlayer())) {
            return false;
        }
        if (!Objects.equals(this.idFootballManager, other.getIdFootballManager())) {
            return false;
        }
        return Objects.equals(this.price, other.getPrice());
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
