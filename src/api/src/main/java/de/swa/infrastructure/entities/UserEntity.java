package de.swa.infrastructure.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@UserDefinition
@Table(name = "users")
public class UserEntity extends PanacheEntity implements Serializable {
    @Column(name = "username", length = 64, unique = true)
    @Username
    public String userName;

    @Column(name = "externalId", unique = true)
    public String externalId;

    @Column(name = "lat")
    public Double lat;

    @Column(name = "lon")
    public Double lon;

    @OneToMany(mappedBy = "userId")
    public List<VotingEntity> votings;

    @Column(name = "passwordHash")
    @Password
    public String passwordHash;

    @Column(name = "isExternal")
    public boolean isExternal;

    @Roles
    public String role;

    public UserEntity() {
        this.votings = new ArrayList<>();
    }

    public UserEntity(String name) {
        this.userName = name;
        this.votings = new ArrayList<>();
    }
}