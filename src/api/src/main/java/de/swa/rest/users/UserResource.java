package de.swa.rest.users;

import de.swa.infrastructure.dao.UserDao;
import de.swa.infrastructure.entities.UserEntity;
import de.swa.rest.users.dto.CreateUserDto;
import io.quarkus.security.identity.SecurityIdentity;
import org.jboss.resteasy.annotations.cache.NoCache;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@RequestScoped
@Path("/users")
public class UserResource {
    @Inject
    UserDao userDao;
   
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserEntity> getUsers() {
        return userDao.getAllUsers();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UserEntity createUser(CreateUserDto user){
        var temp = new UserEntity(user.getUsername());
        return userDao.addUser(temp);
    }
}
