package de.swa.infrastructure.repositories;

import de.swa.infrastructure.entities.PostEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class PostRepository implements PanacheRepository<PostEntity> {
    @Transactional
    public PostEntity createPostFor(Long userId, String user, String text) {
        return this.createPostFor(userId, user, text, "Demo Location");
    }

    @Transactional
    public PostEntity createPostFor(Long userId, String user, String text, String locationText) {
        var entity = new PostEntity(userId, text, user);
        entity.locationText = locationText;
        entity.persist();
        return entity;
    }


    @Transactional
    public PostEntity updateVoteCount(Long id, Integer updateCount) {
        var entity = this.findById(id);
        entity.voteCount += updateCount;
        entity.persist();
        return entity;
    }

    public List<PostEntity> getNewestPost(Integer page, Integer limit) {
        return this.findAll(Sort.descending("createdAt"))
                .page(page, limit)
                .list();
    }

    public List<PostEntity> getMostCommentsPosts(Integer page, Integer limit) {
        return this.findAll(Sort.descending("commentCount").and("createdAt", Sort.Direction.Descending))
                .page(page, limit)
                .list();
    }

    public List<PostEntity> getMostVotesPosts(Integer page, Integer limit) {
        return this.findAll(Sort.descending("voteCount").and("createdAt", Sort.Direction.Descending))
                .page(page, limit)
                .list();
    }

    @Transactional
    public void updateCommentCountByOne(Long id) {
        var entity = this.findById(id);
        entity.commentCount++;
        entity.persist();
    }
}
