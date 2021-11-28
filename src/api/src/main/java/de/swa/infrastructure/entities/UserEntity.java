package de.swa.infrastructure.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "userSeq", sequenceName = "ZSEQ_TUSER_ID", allocationSize = 1, initialValue = 3)
    @GeneratedValue(generator = "userSeq")
    @Column(name = "id")
    private Integer id;

    @Column(name = "username", length = 64, unique = true)
    private String username;

    @Column(name = "externalId", unique = true)
    private String externalId;

    @Column(name = "lat")
    private Double lat;

    @Column(name = "lon")
    private Double lon;

    @OneToMany(mappedBy = "userId")
    private List<VotingEntity> votings;

    public UserEntity() {
        this.votings = new ArrayList<>();
    }

    public UserEntity(String name) {
        this.username = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public String getExternalId() {
        return this.externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }
}