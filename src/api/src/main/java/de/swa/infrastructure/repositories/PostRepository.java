package de.swa.infrastructure.repositories;

import de.swa.infrastructure.entities.PostEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class PostRepository implements PanacheRepository<PostEntity> {
    @Inject
    EntityManager em;
    @Transactional
    public PostEntity createPostFor(Long userId, String user, String text, String color, String locationText, Double lat, Double lon) {
        var entity = new PostEntity(userId, text, user, color);
        entity.locationText = locationText;
        entity.setLat(lat);
        entity.setLon(lon);
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

    public List<PostEntity> getNewestPost(Integer page, Integer limit, Double lat, Double lon) {
        if (lat == null || lon == null)
            return this.findAll(Sort.descending("createdAt"))
                    .page(page, limit)
                    .list();

        return (List<PostEntity>) em.createNativeQuery("select *, (6371 * acos (cos ( radians("+lat+") ) * cos( radians( lat ) ) * cos( radians( lon ) - radians("+lon+") ) + sin ( radians("+lat+") ) * sin( radians( lat ) ) )) as distance from posts having distance < 10 ORDER BY createdAt DESC", PostEntity.class)
                .setFirstResult(page * limit)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<PostEntity> getMostCommentsPosts(Integer page, Integer limit, Double lat, Double lon) {
        if (lat == null || lon == null)
            return this.findAll(Sort.descending("commentCount").and("createdAt", Sort.Direction.Descending))
                .page(page, limit)
                .list();
        return (List<PostEntity>) em.createNativeQuery("select *, (6371 * acos (cos ( radians("+lat+") ) * cos( radians( lat ) ) * cos( radians( lon ) - radians("+lon+") ) + sin ( radians("+lat+") ) * sin( radians( lat ) ) )) as distance from posts having distance < 10 ORDER BY commentCount DESC, createdAt DESC", PostEntity.class)
                .setFirstResult(page * limit)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<PostEntity> getMostVotesPosts(Integer page, Integer limit, Double lat, Double lon) {
        if (lat == null || lon == null)
            return this.findAll(Sort.descending("voteCount").and("createdAt", Sort.Direction.Descending))
                .page(page, limit)
                .list();

        return (List<PostEntity>) em.createNativeQuery("select *, (6371 * acos (cos ( radians("+lat+") ) * cos( radians( lat ) ) * cos( radians( lon ) - radians("+lon+") ) + sin ( radians("+lat+") ) * sin( radians( lat ) ) )) as distance from posts having distance < 10 ORDER BY voteCount DESC, createdAt DESC", PostEntity.class)
                .setFirstResult(page * limit)
                .setMaxResults(limit)
                .getResultList();
    }

    @Transactional
    public void updateCommentCountByOne(Long id) {
        var entity = this.findById(id);
        entity.commentCount++;
        entity.persist();
    }
}
