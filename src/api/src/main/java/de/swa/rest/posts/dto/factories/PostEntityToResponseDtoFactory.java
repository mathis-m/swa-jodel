package de.swa.rest.posts.dto.factories;

import de.swa.infrastructure.entities.PostEntity;
import de.swa.rest.posts.dto.PostResponseDto;

public class PostEntityToResponseDtoFactory {
    public static PostResponseDto map(PostEntity post) {
        var dto = new PostResponseDto();
        dto.setId(post.id);
        dto.setUserId(post.userId);
        dto.setText(post.text);
        dto.setVoteCount(post.voteCount);
        dto.setCreatedAt(post.createdAt);
        dto.setCommentCount(post.commentCount);
        dto.setUser(post.user);
        dto.setLocationText(post.locationText);
        return dto;
    }
}
