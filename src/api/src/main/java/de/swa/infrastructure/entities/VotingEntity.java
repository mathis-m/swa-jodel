package de.swa.infrastructure.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;

@Entity
@Table(name = "votings")
public class VotingEntity extends PanacheEntity {
    public VotingEntity() {}
    public VotingEntity(Long userId, Long postId, boolean isUpVote) {
        this.value = isUpVote ? 1 : -1;
        this.userId = userId;
        this.postId = postId;
    }

    @Column(name = "userId")
    public Long userId;

    @Column(name = "postId")
    public Long postId;

    @Column(name = "value")
    public Integer value;
}
