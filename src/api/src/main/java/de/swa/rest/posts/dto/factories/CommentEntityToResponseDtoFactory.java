package de.swa.rest.posts.dto.factories;

import de.swa.infrastructure.entities.CommentEntity;
import de.swa.infrastructure.entities.PostEntity;
import de.swa.rest.posts.dto.CommentResponseDto;
import de.swa.rest.posts.dto.PostResponseDto;

public class CommentEntityToResponseDtoFactory {
    public static CommentResponseDto map(CommentEntity comment) {
        var dto = new CommentResponseDto();
        dto.setId(comment.id);
        dto.setPostId(comment.postId);
        dto.setUser(comment.user);
        dto.setText(comment.text);
        dto.setCreatedAt(comment.createdAt);
        return dto;
    }
}
