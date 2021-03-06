package de.swa.rest.users;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import de.swa.rest.users.dto.UpdateLocationDto;
import de.swa.services.TokenValidationService;
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
    UserContextService userContextService;

    @Inject
    TokenValidationService tokenValidationService;

    @Authenticated
    @SecurityRequirements({
            @SecurityRequirement(
                    name = "CookieAuth"
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

    @Authenticated
    @SecurityRequirements({
            @SecurityRequirement(
                    name = "CookieAuth"
            )
    })
    @POST
    @Path("/my/location")
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse<UserResponseDto> updateLocation(UpdateLocationDto locationDto) {
        var currentUser = userContextService.getCurrentUser();

        var updated = userRepository.updateLocation(currentUser.id, locationDto.getLat(), locationDto.getLon());

        return ResponseBuilder
                .ok(UserEntityToResponseDtoFactory.map(updated), MediaType.APPLICATION_JSON_TYPE)
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
            googleIdToken = tokenValidationService.getGoogleIdToken(token);
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

    @POST()
    @Path("/register/facebook")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse<String> createUserByFacebook(
            CreateExternalUserDto user,
            @RestHeader("Authorization") String authHeader
    ) {
        RestResponse<String> provideValidFacebookBearer = ResponseBuilder
                .create(RestResponse.Status.UNAUTHORIZED, "Please provide valid Facebook Bearer")
                .build();
        if (authHeader == null) {
            return provideValidFacebookBearer;
        }
        var token = authHeader.replace("Bearer ", "");
        String facebookId = null;
        try {
            facebookId = tokenValidationService.getFacebookId(token);
        } catch (Exception e) {
            return provideValidFacebookBearer;
        }
        if (facebookId == null) {
            return provideValidFacebookBearer;
        }

        try {
            userRepository.createExternalUser(user.getUserName(), facebookId);
        } catch (UniqueUserNameRequiredException | UniqueExternalIdRequiredException uniqueException) {
            return ResponseBuilder
                    .create(RestResponse.Status.CONFLICT, uniqueException.getMessage())
                    .build();
        }

        return ResponseBuilder
                .ok("Facebook user successful registered!", MediaType.TEXT_PLAIN)
                .build();
    }
}
