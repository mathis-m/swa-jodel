package de.swa.rest.posts.dto.factories;

import de.swa.infrastructure.entities.VotingEntity;
import de.swa.infrastructure.repositories.models.PostVotingModel;
import de.swa.rest.posts.dto.PostVotingResponseDto;
import de.swa.rest.posts.dto.VotingResponseDto;

public class VotingEntityToResponseDtoFactory {
    public static VotingResponseDto map(VotingEntity voting, Long postId) {
        var dto = new VotingResponseDto();

        if(voting == null) {
            dto.setPostId(postId);
            dto.setHasUpVoted(false);
            dto.setHasDownVoted(false);
            return dto;
        }

        dto.setPostId(voting.postId);
        dto.setHasUpVoted(voting.value == 1);
        dto.setHasDownVoted(voting.value == -1);
        return dto;
    }

    public static PostVotingResponseDto mapPostVoting(PostVotingModel postVotingModel) {
        var dto = new PostVotingResponseDto();
        dto.setVoting(VotingEntityToResponseDtoFactory.map(postVotingModel.voting, postVotingModel.post.id));
        dto.setPost(PostEntityToResponseDtoFactory.map(postVotingModel.post));
        return dto;
    }
}
