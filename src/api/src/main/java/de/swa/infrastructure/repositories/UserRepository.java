package de.swa.infrastructure.repositories;

import de.swa.infrastructure.entities.UserEntity;
import de.swa.infrastructure.repositories.exceptions.UniqueExternalIdRequiredException;
import de.swa.infrastructure.repositories.exceptions.UniqueUserNameRequiredException;
import de.swa.infrastructure.repositories.exceptions.UserNotFoundException;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
public class UserRepository implements PanacheRepository<UserEntity> {
    @Transactional
    public UserEntity createLocalUser(String userName, String password) throws UniqueUserNameRequiredException {
        var userNameExistCount = this.count("username", userName);
        if (userNameExistCount > 0) {
           throw new UniqueUserNameRequiredException(userName);
        }
        var entity = new UserEntity();
        entity.userName = userName;
        entity.passwordHash = BcryptUtil.bcryptHash(password);
        entity.isExternal = false;
        entity.role = "user";
        entity.persist();
        return entity;
    }

    public UserEntity getUserByName(String userName) throws UserNotFoundException {
        var user = this
                .find("userName", userName)
                .firstResult();
        if (user == null) {
           throw new UserNotFoundException();
        }
        return user;
    }

    public UserEntity getUserByExternalId(String externalId) throws UserNotFoundException {
        var user = this
                .find("externalId",externalId)
                .firstResult();
        if (user == null) {
           throw new UserNotFoundException();
        }
        return user;
    }

    @Transactional
    public void createExternalUser(String userName, String externalId) throws UniqueUserNameRequiredException, UniqueExternalIdRequiredException {
        var userNameExistCount = this.count("username", userName);
        var externalIdExistCount = this.count("externalId", externalId);
        if (userNameExistCount > 0) {
            throw new UniqueUserNameRequiredException(userName);
        }
        if (externalIdExistCount > 0) {
            throw new UniqueExternalIdRequiredException(externalId);
        }
        var entity = new UserEntity();
        entity.userName = userName;
        entity.externalId = externalId;
        entity.isExternal = true;
        entity.role = "user";
        entity.persist();
    }

    @Transactional
    public UserEntity updateLocation(Long userId, Double lat, Double lon) {
        var userEntity = this.findById(userId);
        userEntity.setLat(lat);
        userEntity.setLon(lon);
        userEntity.persist();
        return userEntity;
    }
}
