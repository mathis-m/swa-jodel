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
import de.swa.rest.posts.Colors;
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
            tUser = userRepository.updateLocation(tUser.id, 48.7900268, 9.2538728);
            var t1User = userRepository.createLocalUser("test1", "test1");
            t1User = userRepository.updateLocation(t1User.id, 48.7900268, 9.2538728);
            var t2User = userRepository.createLocalUser("test2", "test2");
            t2User = userRepository.updateLocation(t2User.id, 48.7900268, 9.2538728);
            var t3User = userRepository.createLocalUser("test3", "test3");
            t3User = userRepository.updateLocation(t3User.id, 48.7900268, 9.2538728);
            var t4User = userRepository.createLocalUser("test4", "test4");
            t4User = userRepository.updateLocation(t4User.id, 48.7900268, 9.2538728);
            Random rand = new Random();
            Colors[] colors = Colors.values();
            for (int i = 0; i < 15; i++) {
                var p1 = postRepository.createPostFor(tUser.id, tUser.userName, "This is a very cool yodel by " + tUser.userName + "!!! #" + (i + 1), colors[i % colors.length].toString(), "Rotenberg", tUser.lat, tUser.lon);
                int max = rand.nextInt(7);
                addNComments(tUser, max, p1);
                addVote5050(tUser, rand, p1);
                addVote5050(t1User, rand, p1);
                addVote5050(t2User, rand, p1);
                addVote5050(t3User, rand, p1);
                addVote5050(t4User, rand, p1);
                var p2 = postRepository.createPostFor(t1User.id, t1User.userName, "This is a very cool yodel by " + t1User.userName + "!!! #" + (i + 1), colors[(i+3) % colors.length].toString(), "Rotenberg", t1User.lat, t1User.lon);
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