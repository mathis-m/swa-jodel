package de.swa;

import de.swa.infrastructure.entities.PostEntity;
import de.swa.infrastructure.entities.UserEntity;
import de.swa.infrastructure.repositories.CommentRepository;
import de.swa.infrastructure.repositories.PostRepository;
import de.swa.infrastructure.repositories.UserRepository;
import de.swa.infrastructure.repositories.VotingRepository;
import de.swa.infrastructure.repositories.exceptions.UniqueUserNameRequiredException;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;


@QuarkusTest
public class CommentRepositoryTests {
    @Inject
    CommentRepository repo;
    @Inject
    PostRepository postRepo;
    @Inject
    UserRepository userRepository;

    PostEntity postToComment;
    UserEntity user;

    @Transactional
    @BeforeEach
    void setup() {
        try {
            userRepository.deleteAll();
            postRepo.deleteAll();
            repo.deleteAll();
            user = userRepository.createLocalUser("test", "test");
            postToComment = postRepo.createPostFor(user.id, user.userName, "hallo", "black", "Some", 0.0, 0.0);

        } catch (UniqueUserNameRequiredException e) {
            e.printStackTrace();
        }
    }

    @Transactional
    @Test
    public void getCommentsOfPost_returnEmptyList_ifNewPost() {
        var comments = repo.getCommentsOfPost(postToComment.id);
        Assertions.assertEquals(0, comments.size());
    }

    @Transactional
    @Test
    public void getCommentsOfPost_returnListWithOneComment_ifCreated() {
        String expectedComment = "comment text";
        repo.createComment(postToComment.id, user.id, user.userName, expectedComment);
        var comments = repo.getCommentsOfPost(postToComment.id);
        Assertions.assertTrue(comments.stream().findFirst().isPresent());
        Assertions.assertEquals(expectedComment, comments.stream().findFirst().get().text);
    }

    @Transactional
    @Test
    public void createComment_AddsComment() {
        String expectedComment = "comment text";
        repo.createComment(postToComment.id, user.id, user.userName, expectedComment);
        var comments = repo.getCommentsOfPost(postToComment.id);
        Assertions.assertTrue(comments.stream().findFirst().isPresent());
        Assertions.assertEquals(expectedComment, comments.stream().findFirst().get().text);
    }
}