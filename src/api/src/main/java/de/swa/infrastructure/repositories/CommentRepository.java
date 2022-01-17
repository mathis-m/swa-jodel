package de.swa.infrastructure.repositories;

import de.swa.infrastructure.entities.CommentEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class CommentRepository implements PanacheRepository<CommentEntity> {
    public List<CommentEntity> getCommentsOfPost(Long postId) {
        return this
            .find("postId", Sort.descending("createdAt"), postId)
            .list();
    }

    @Transactional
    public CommentEntity createComment(Long postId, Long userId, String user, String text) {
        var entity = new CommentEntity();

        entity.createdAt = LocalDateTime.now();
        entity.postId = postId;
        entity.userId = userId;
        entity.user = user;
        entity.text = text;
        entity.persist();

        return entity;
    }
}
