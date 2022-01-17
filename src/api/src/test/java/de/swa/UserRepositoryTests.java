package de.swa;

import de.swa.infrastructure.repositories.UserRepository;
import de.swa.infrastructure.repositories.exceptions.UniqueExternalIdRequiredException;
import de.swa.infrastructure.repositories.exceptions.UniqueUserNameRequiredException;
import de.swa.infrastructure.repositories.exceptions.UserNotFoundException;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;


@QuarkusTest
public class UserRepositoryTests {
    @Inject
    UserRepository userRepository;

    @Transactional
    @BeforeEach
    void setup() {
        userRepository.deleteAll();
    }

    @Transactional
    @Test
    public void createLocalUser_shouldCreateUser_ifUniqueUsername() {
        Assertions.assertDoesNotThrow(() -> userRepository.createLocalUser("test", "test"));
    }

    @Transactional
    @Test
    public void createExternalUser_shouldCreateUser_ifUniqueUsername() {
        Assertions.assertDoesNotThrow(() -> userRepository.createExternalUser("test", "someId"));
    }

    @Transactional
    @Test
    public void createLocalUser_shouldThrow_ifNotUniqueUsername() {
        Assertions.assertDoesNotThrow(() -> userRepository.createLocalUser("test", "test"));
        Assertions.assertThrows(UniqueUserNameRequiredException.class, () -> userRepository.createLocalUser("test", "test"));
    }

    @Transactional
    @Test
    public void createExternalUser_shouldThrow_ifNotUniqueUsername() {
        Assertions.assertDoesNotThrow(() -> userRepository.createExternalUser("test", "someId"));
        Assertions.assertThrows(UniqueUserNameRequiredException.class, () -> userRepository.createExternalUser("test", "otherId"));
    }

    @Transactional
    @Test
    public void createExternalUser_shouldThrow_ifNotUniqueExternalId() {
        Assertions.assertDoesNotThrow(() -> userRepository.createExternalUser("test", "someId"));
        Assertions.assertThrows(UniqueExternalIdRequiredException.class, () -> userRepository.createExternalUser("other", "someId"));
    }

    @Transactional
    @Test
    public void createLocalUser_shouldBeNotExternal() {
        var user = Assertions.assertDoesNotThrow(() -> userRepository.createLocalUser("test", "test"));
        Assertions.assertFalse(user.isExternal);
    }

    @Transactional
    @Test
    public void createLocalUser_shouldBeExternal() {
        Assertions.assertDoesNotThrow(() -> userRepository.createExternalUser("test", "someId"));
        var user = Assertions.assertDoesNotThrow(() -> userRepository.getUserByExternalId("someId"));
        Assertions.assertTrue(user.isExternal);
    }

    @Transactional
    @Test
    public void getUserByName_shouldThrow_ifNotExists() {
        Assertions.assertThrows(UserNotFoundException.class, () -> userRepository.getUserByName("test"));
    }

    @Transactional
    @Test
    public void getUserByName_shouldNotThrow_ifExists() {
        var user = Assertions.assertDoesNotThrow(() -> userRepository.createLocalUser("test", "test"));
        Assertions.assertDoesNotThrow(() -> userRepository.getUserByName(user.userName));
    }

    @Transactional
    @Test
    public void getUserByExternalId_shouldThrow_ifNotExists() {
        Assertions.assertThrows(UserNotFoundException.class, () -> userRepository.getUserByExternalId("test"));
    }

    @Transactional
    @Test
    public void getUserByExternalId_shouldNotThrow_ifExists() {
        Assertions.assertDoesNotThrow(() -> userRepository.createExternalUser("test", "someId"));
        Assertions.assertDoesNotThrow(() -> userRepository.getUserByExternalId("someId"));
    }

    @Transactional
    @Test
    public void updateLocation_shouldUpdateLocation() {
        var user = Assertions.assertDoesNotThrow(() -> userRepository.createLocalUser("test", "test"));
        var lat = 1.0;
        var lon = 2.0;
        var updated = userRepository.updateLocation(user.id, lat, lon);
        Assertions.assertEquals(lat, updated.lat);
        Assertions.assertEquals(lon, updated.lon);
    }
}