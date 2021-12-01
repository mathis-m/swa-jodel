package de.swa.rest.users;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import de.swa.auth.GoogleTokenValidator;
import de.swa.infrastructure.repositories.UserRepository;
import de.swa.infrastructure.repositories.exceptions.UniqueExternalIdRequiredException;
import de.swa.infrastructure.repositories.exceptions.UniqueUserNameRequiredException;
import de.swa.rest.users.dto.CreateExternalUserDto;
import de.swa.rest.users.dto.CreateLocalUserDto;
import de.swa.rest.users.dto.UserResponseDto;
import de.swa.rest.users.dto.factories.UserEntityToResponseDtoFactory;
import de.swa.services.UserContextService;
import io.quarkus.security.Authenticated;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirements;
import org.jboss.resteasy.reactive.RestHeader;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.RestResponse.ResponseBuilder;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@RequestScoped
@Path("/users")
public class UserResource {
    @Inject
    UserRepository userRepository;

    @Inject
    GoogleTokenValidator googleTokenValidator;

    @Inject
    UserContextService userContextService;

    @Authenticated
    @SecurityRequirements({
            @SecurityRequirement(
                    name = "Google"
            ),
            @SecurityRequirement(
                    name = "Local"
            )
    })
    @GET
    @Path("/me")
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse<UserResponseDto> getMe() {
        var currentUser = userContextService.getCurrentUser();

        return ResponseBuilder
                .ok(UserEntityToResponseDtoFactory.map(currentUser), MediaType.APPLICATION_JSON_TYPE)
                .build();
    }

    @POST()
    @Path("/register/local")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public RestResponse<String> createUser(CreateLocalUserDto user) {
        try {
            userRepository.createLocalUser(user.getUsername(), user.getPassword());
        } catch (UniqueUserNameRequiredException userNameNotUnique) {
            return ResponseBuilder
                    .create(RestResponse.Status.CONFLICT, userNameNotUnique.getMessage())
                    .build();
        }
        return ResponseBuilder
                .ok("User created!", MediaType.TEXT_PLAIN)
                .status(RestResponse.Status.CREATED)
                .build();
    }

    @SecurityRequirement(
            name = "Google"
    )
    @POST()
    @Path("/register/google")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse<String> createUserByGoogle(
            CreateExternalUserDto user,
            @RestHeader("Authorization") String authHeader
    ) {
        RestResponse<String> provideValidGoogleBearer = ResponseBuilder
                .create(RestResponse.Status.UNAUTHORIZED, "Please provide valid Google Bearer")
                .build();
        if (authHeader == null) {
            return provideValidGoogleBearer;
        }
        var token = authHeader.replace("Bearer ", "");
        GoogleIdToken googleIdToken = null;
        try {
            googleIdToken = googleTokenValidator.yodelVerifier.verify(token);
        } catch (Exception e) {
            return provideValidGoogleBearer;
        }
        if (googleIdToken == null) {
            return provideValidGoogleBearer;
        }

        try {
            userRepository.createExternalUser(user.getUserName(), googleIdToken.getPayload().getSubject());
        } catch (UniqueUserNameRequiredException | UniqueExternalIdRequiredException uniqueException) {
            return ResponseBuilder
                    .create(RestResponse.Status.CONFLICT, uniqueException.getMessage())
                    .build();
        }

        return ResponseBuilder
                .ok("Google user successful registered!", MediaType.TEXT_PLAIN)
                .build();
    }
}
