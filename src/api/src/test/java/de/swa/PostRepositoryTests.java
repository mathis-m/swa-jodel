package de.swa;

import de.swa.infrastructure.entities.PostEntity;
import de.swa.infrastructure.entities.UserEntity;
import de.swa.infrastructure.repositories.CommentRepository;
import de.swa.infrastructure.repositories.PostRepository;
import de.swa.infrastructure.repositories.UserRepository;
import de.swa.infrastructure.repositories.exceptions.UniqueUserNameRequiredException;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;


@QuarkusTest
public class PostRepositoryTests {
    @Inject
    PostRepository repo;
    @Inject
    UserRepository userRepository;
    @Inject
    CommentRepository commentRepository;

    UserEntity user;

    Double refLat = 48.70151890822699;
    Double refLon = 9.651338794619095;

    Double lessThen10KmLat = 48.665166017076416;
    Double lessThen10KmLon = 9.765646760111466;

    Double moreThen10KmLat = 48.65284080659227;
    Double moreThen10KmLon = 9.78332136485326;

    @Transactional
    @BeforeEach
    void setup() {
        try {
            userRepository.deleteAll();
            commentRepository.deleteAll();
            repo.deleteAll();
            user = userRepository.createLocalUser("test", "test");

        } catch (UniqueUserNameRequiredException e) {
            e.printStackTrace();
        }
    }

    @Transactional
    @Test
    public void createPostFor_shouldCreatePost() {
        var created = repo.createPostFor(user.id, user.userName, "hallo", "black", "Some", 0.0, 0.0);
        var post = repo.findById(created.id);
        Assertions.assertEquals(created.text, post.text);
    }

    @Transactional
    @Test
    public void updateVoteCount_shouldAddVote() {
        var post = repo.createPostFor(user.id, user.userName, "hallo", "black", "Some", 0.0, 0.0);
        Assertions.assertEquals(0, post.voteCount);
        repo.updateVoteCount(post.id, 1);
        Assertions.assertEquals(1, post.voteCount);
    }

    @Transactional
    @Test
    public void updateVoteCount_shouldRemoveVote() {
        var post = repo.createPostFor(user.id, user.userName, "hallo", "black", "Some", 0.0, 0.0);
        Assertions.assertEquals(0, post.voteCount);
        repo.updateVoteCount(post.id, -1);
        Assertions.assertEquals(-1, post.voteCount);
    }

    @Transactional
    @Test
    public void getNewestPost_correctSorting() {
        var post1 = repo.createPostFor(user.id, user.userName, "1", "black", "Some", 0.0, 0.0);
        var post2 = repo.createPostFor(user.id, user.userName, "2", "black", "Some", 0.0, 0.0);
        var posts = repo.getNewestPost(0, 10, 0.0, 0.0);
        Assertions.assertEquals(post2.text, posts.get(0).text);
        Assertions.assertEquals(post1.text, posts.get(1).text);
    }

    @Transactional
    @Test
    public void getNewestPost_shouldShow_ifIn10KmRange() {
        var post1 = repo.createPostFor(user.id, user.userName, "1", "black", "Some", lessThen10KmLat, lessThen10KmLon);
        var posts = repo.getNewestPost(0, 10, refLat, refLon);
        Assertions.assertEquals(1, posts.size());
        Assertions.assertEquals(post1.text, posts.get(0).text);
    }

    @Transactional
    @Test
    public void getNewestPost_shouldNotShow_ifMoreThan10KmRange() {
        var post1 = repo.createPostFor(user.id, user.userName, "1", "black", "Some", moreThen10KmLat, moreThen10KmLon);
        var posts = repo.getNewestPost(0, 10, refLat, refLon);
        Assertions.assertEquals(0, posts.size());
    }

    @Transactional
    @Test
    public void getMostCommentsPosts_correctSorting() {
        var post1 = repo.createPostFor(user.id, user.userName, "1", "black", "Some", 0.0, 0.0);
        commentRepository.createComment(post1.id, user.id, user.userName, "Comment");
        commentRepository.createComment(post1.id, user.id, user.userName, "Comment");
        var post2 = repo.createPostFor(user.id, user.userName, "2", "black", "Some", 0.0, 0.0);
        commentRepository.createComment(post2.id, user.id, user.userName, "Comment");
        commentRepository.createComment(post2.id, user.id, user.userName, "Comment");
        commentRepository.createComment(post2.id, user.id, user.userName, "Comment");
        var posts = repo.getMostCommentsPosts(0, 10, 0.0, 0.0);
        Assertions.assertEquals(post2.text, posts.get(0).text);
        Assertions.assertEquals(post1.text, posts.get(1).text);
    }

    @Transactional
    @Test
    public void getMostCommentsPosts_shouldShow_ifIn10KmRange() {
        var post1 = repo.createPostFor(user.id, user.userName, "1", "black", "Some", lessThen10KmLat, lessThen10KmLon);
        var posts = repo.getMostCommentsPosts(0, 10, refLat, refLon);
        Assertions.assertEquals(1, posts.size());
        Assertions.assertEquals(post1.text, posts.get(0).text);
    }

    @Transactional
    @Test
    public void getMostCommentsPosts_shouldNotShow_ifMoreThan10KmRange() {
        var post1 = repo.createPostFor(user.id, user.userName, "1", "black", "Some", moreThen10KmLat, moreThen10KmLon);
        var posts = repo.getMostCommentsPosts(0, 10, refLat, refLon);
        Assertions.assertEquals(0, posts.size());
    }

    @Transactional
    @Test
    public void getMostVotesPosts_correctSorting() {
        var post1 = repo.createPostFor(user.id, user.userName, "1", "black", "Some", 0.0, 0.0);
        repo.updateVoteCount(post1.id, 10);
        var post2 = repo.createPostFor(user.id, user.userName, "2", "black", "Some", 0.0, 0.0);
        repo.updateVoteCount(post2.id, 20);
        var posts = repo.getMostVotesPosts(0, 10, 0.0, 0.0);
        Assertions.assertEquals(post2.text, posts.get(0).text);
        Assertions.assertEquals(post1.text, posts.get(1).text);
    }

    @Transactional
    @Test
    public void getMostVotesPosts_shouldShow_ifIn10KmRange() {
        var post1 = repo.createPostFor(user.id, user.userName, "1", "black", "Some", lessThen10KmLat, lessThen10KmLon);
        var posts = repo.getMostVotesPosts(0, 10, refLat, refLon);
        Assertions.assertEquals(1, posts.size());
        Assertions.assertEquals(post1.text, posts.get(0).text);
    }

    @Transactional
    @Test
    public void getMostVotesPosts_shouldNotShow_ifMoreThan10KmRange() {
        var post1 = repo.createPostFor(user.id, user.userName, "1", "black", "Some", moreThen10KmLat, moreThen10KmLon);
        var posts = repo.getMostVotesPosts(0, 10, refLat, refLon);
        Assertions.assertEquals(0, posts.size());
    }
}