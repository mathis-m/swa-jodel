package de.swa.infrastructure.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
public class CommentEntity extends PanacheEntity {
    public CommentEntity() {}

    @Column(name = "userId")
    public Long userId;

    @Column(name = "user")
    public String user;

    @Column(name = "postId")
    public Long postId;

    @Lob
    @Column(name = "text")
    public String text;

    @Column(name = "createdAt")
    public LocalDateTime createdAt;
}
