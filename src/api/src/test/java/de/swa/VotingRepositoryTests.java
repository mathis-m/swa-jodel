package de.swa;

import de.swa.infrastructure.entities.PostEntity;
import de.swa.infrastructure.entities.UserEntity;
import de.swa.infrastructure.repositories.PostRepository;
import de.swa.infrastructure.repositories.UserRepository;
import de.swa.infrastructure.repositories.VotingRepository;
import de.swa.infrastructure.repositories.exceptions.UniqueUserNameRequiredException;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;


@QuarkusTest
public class VotingRepositoryTests {
    @Inject
    VotingRepository repo;
    @Inject
    PostRepository postRepo;
    @Inject
    UserRepository userRepository;

    PostEntity postToVote;
    UserEntity user;

    @Transactional
    @BeforeEach
    void setup() {
        try {
            userRepository.deleteAll();
            postRepo.deleteAll();
            repo.deleteAll();
            user = userRepository.createLocalUser("test", "test");
            postToVote = postRepo.createPostFor(user.id, user.userName, "hallo", "black", "Some", 0.0, 0.0);

        } catch (UniqueUserNameRequiredException e) {
            e.printStackTrace();
        }
    }

    @Transactional
    @Test
    public void votePost_createsUpVote_ifNotVotedBefore() {
        repo.votePost(postToVote.id, user.id, true);
        var postVoting = repo.getVoteOfPost(postToVote.id, user.id);
        var post = postRepo.findById(postToVote.id);
        Assertions.assertEquals(1, postVoting.value);
        Assertions.assertEquals(1, post.voteCount);
    }

    @Transactional
    @Test
    public void votePost_createsDownVote_ifNotVotedBefore() {
        repo.votePost(postToVote.id, user.id, false);
        var postVoting = repo.getVoteOfPost(postToVote.id, user.id);
        var post = postRepo.findById(postToVote.id);
        Assertions.assertEquals(-1, postVoting.value);
        Assertions.assertEquals(-1, post.voteCount);
    }

    @Transactional
    @Test
    public void votePost_revertsUpVote_ifUpVotedAgain() {
        repo.votePost(postToVote.id, user.id, true);
        repo.votePost(postToVote.id, user.id, true);
        var postVoting = repo.getVoteOfPost(postToVote.id, user.id);
        var post = postRepo.findById(postToVote.id);
        Assertions.assertEquals(0, postVoting.value);
        Assertions.assertEquals(0, post.voteCount);
    }

    @Transactional
    @Test
    public void votePost_revertsDownVote_ifDownVotedAgain() {
        repo.votePost(postToVote.id, user.id, false);
        repo.votePost(postToVote.id, user.id, false);
        var postVoting = repo.getVoteOfPost(postToVote.id, user.id);
        var post = postRepo.findById(postToVote.id);
        Assertions.assertEquals(0, postVoting.value);
        Assertions.assertEquals(0, post.voteCount);
    }

}