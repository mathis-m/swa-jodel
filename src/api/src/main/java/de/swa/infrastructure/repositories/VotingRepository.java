package de.swa.infrastructure.repositories;

import de.swa.infrastructure.entities.VotingEntity;
import de.swa.infrastructure.repositories.models.PostVotingModel;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@ApplicationScoped
public class VotingRepository implements PanacheRepository<VotingEntity> {
    @Inject
    PostRepository postRepository;
    @Transactional
    public PostVotingModel votePost(Long postId, Long userId, boolean isUpVote) {
        var voting = this
                .find("userId = ?1 and postId = ?2", userId, postId).firstResult();
        var correction = 0;
        var model = new PostVotingModel();
        if (voting == null) {
            var entity = new VotingEntity(userId, postId, isUpVote);
            entity.persist();
            correction = isUpVote ? 1 : -1;
            model.voting = entity;
        } else {
            if (voting.value == 0) {
                voting.value = isUpVote ? 1 : -1;
                voting.persist();
                correction = isUpVote ? 1 : -1;
            } else if (voting.value == 1) {
                voting.value = isUpVote ? 0 : -1;
                voting.persist();
                correction = isUpVote ? -1 : -2;
            } else if (voting.value == -1) {
                voting.value = isUpVote ? 1 : 0;
                voting.persist();
                correction = isUpVote ? 2 : 1;
            }
            model.voting = voting;
        }
        var post= postRepository.updateVoteCount(postId, correction);
        model.post = post;
        return model;
    }

    public VotingEntity getVoteOfPost(Long postId, Long userId) {
        return this
                .find("userId = ?1 and postId = ?2", userId, postId)
                .firstResult();
    }
}
