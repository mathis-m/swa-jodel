package de.swa;


import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.transaction.Transactional;

import de.swa.infrastructure.repositories.UserRepository;
import de.swa.infrastructure.repositories.exceptions.UniqueUserNameRequiredException;
import io.quarkus.runtime.StartupEvent;


@Singleton
public class Startup {
    @Inject
    UserRepository userRepository;

    @Transactional
    public void loadUsers(@Observes StartupEvent evt) {
        // reset and load all test users
        userRepository.deleteAll();
        try {
            userRepository.createLocalUser("test", "test");
            userRepository.createLocalUser("test1", "test1");
        } catch (UniqueUserNameRequiredException e) {
            e.printStackTrace();
        }
    }
}