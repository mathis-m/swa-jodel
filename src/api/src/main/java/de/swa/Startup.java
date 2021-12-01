package de.swa;


import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.transaction.Transactional;

import de.swa.infrastructure.entities.PostEntity;
import de.swa.infrastructure.entities.UserEntity;
import de.swa.infrastructure.repositories.CommentRepository;
import de.swa.infrastructure.repositories.PostRepository;
import de.swa.infrastructure.repositories.UserRepository;
import de.swa.infrastructure.repositories.VotingRepository;
import de.swa.infrastructure.repositories.exceptions.UniqueUserNameRequiredException;
import io.quarkus.runtime.StartupEvent;

import java.util.Random;


@Singleton
public class Startup {
    @Inject
    UserRepository userRepository;
    @Inject
    PostRepository postRepository;
    @Inject
    CommentRepository commentRepository;
    @Inject
    VotingRepository votingRepository;

    @Transactional
    public void loadUsers(@Observes StartupEvent evt) {
        // reset and load all test users
        userRepository.deleteAll();
        try {
            var tUser = userRepository.createLocalUser("test", "test");
            var t1User = userRepository.createLocalUser("test1", "test1");
            var t2User = userRepository.createLocalUser("test2", "test2");
            var t3User = userRepository.createLocalUser("test3", "test3");
            var t4User = userRepository.createLocalUser("test4", "test4");
            Random rand = new Random();
            for (int i = 0; i < 15; i++) {
                var p1 = postRepository.createPostFor(tUser.id, tUser.userName, "This is a very cool yodel by " + tUser.userName + "!!! #" + (i + 1));
                int max = rand.nextInt(7);
                addNComments(tUser, max, p1);
                addVote5050(tUser, rand, p1);
                addVote5050(t1User, rand, p1);
                addVote5050(t2User, rand, p1);
                addVote5050(t3User, rand, p1);
                addVote5050(t4User, rand, p1);
                var p2 = postRepository.createPostFor(t1User.id, t1User.userName, "This is a very cool yodel by " + t1User.userName + "!!! #" + (i + 1));
                max = rand.nextInt(4);
                addNComments(tUser, max, p2);
                addVote5050(t1User, rand, p2);
                addVote5050(t2User, rand, p2);
                addVote5050(t3User, rand, p2);
                addVote5050(t4User, rand, p2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addVote5050(UserEntity t1User, Random rand, PostEntity p2) {
        if (rand.nextInt(2) == 0) {
            votingRepository.votePost(p2.id, t1User.id, true);
        }
    }

    private void addNComments(UserEntity tUser, int max, PostEntity p2) {
        for (int j = 0; j < max; j++) {
            commentRepository.createComment(p2.id, tUser.id, tUser.userName, "Very cool comment by " + tUser.userName + "!!! #" + j + 1);
            postRepository.updateCommentCountByOne(p2.id);
        }
    }
}