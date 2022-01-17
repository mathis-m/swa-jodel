package de.swa.infrastructure.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
public class PostEntity extends PanacheEntity {
    public PostEntity() {}
    public PostEntity(Long userId, String text, String userName, String color) {
        this.userId = userId;
        this.user = userName;
        this.color = color;
        this.text = text;
        this.voteCount = 0L;
        this.commentCount = 0L;
        this.createdAt = LocalDateTime.now();
    }

    @Column(name = "userId")
    public Long userId;

    @Column(name = "user")
    public String user;

    @Column(name = "color")
    public String color;

    @Column(name = "voteCount")
    public Long voteCount;

    @Column(name = "commentCount")
    public Long commentCount;

    @Column(name = "text")
    @Lob
    public String text;

    @Column(name = "locationText")
    public String locationText;

    @Column(name = "createdAt")
    public LocalDateTime createdAt;

    @Column(name = "lat")
    public Double lat;

    @Column(name = "lon")
    public Double lon;

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
