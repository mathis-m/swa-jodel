package de.swa.infrastructure.entities;

import javax.persistence.*;

@Entity
@Table(name = "votings")
public class VotingEntity {
    public VotingEntity() {
        this.value = 0;
    }
    public VotingEntity(Integer userId, boolean isUpVote) {
        this.value = isUpVote ? 1 : -1;
        this.userId = userId;
    }
    @Id
    @SequenceGenerator(name = "votingSeq", sequenceName = "ZSEQ_TUSER_ID", allocationSize = 1, initialValue = 3)
    @GeneratedValue(generator = "votingSeq")
    @Column(name = "id")
    private Integer id;

    @Column(name = "userId")
    private Integer userId;

    @Column(name = "value")
    private Integer value;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
