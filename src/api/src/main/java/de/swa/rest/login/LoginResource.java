package de.swa.rest.login;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import de.swa.infrastructure.entities.UserEntity;
import de.swa.infrastructure.repositories.UserRepository;
import de.swa.infrastructure.repositories.exceptions.UserNotFoundException;
import de.swa.services.TokenService;
import de.swa.services.TokenValidationService;
import io.quarkus.security.UnauthorizedException;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.jboss.resteasy.reactive.RestHeader;
import org.jboss.resteasy.reactive.RestResponse;
import org.wildfly.security.password.Password;
import org.wildfly.security.password.PasswordFactory;
import org.wildfly.security.password.WildFlyElytronPasswordProvider;
import org.wildfly.security.password.interfaces.BCryptPassword;
import org.wildfly.security.password.util.ModularCrypt;

import javax.annotation.security.PermitAll;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

@RequestScoped
@Path("/login")
public class LoginResource {
    protected static final String BEARER = "Bearer";
    protected static final Integer BEARER_PREFIX_LENGTH = BEARER.length() + 1;
    protected static final String BASIC = "Basic";
    protected static final Integer BASIC_PREFIX_LENGTH = BASIC.length() + 1;
    protected static final String AUTHORIZATION_HEADER = "Authorization";

    @Inject
    UserRepository userRepository;

    @Inject
    TokenService tokenService;

    @Inject
    TokenValidationService tokenValidationService;


    @SecurityRequirement(
            name = "Local"
    )
    @POST()
    @Path("/local")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @PermitAll
    public RestResponse<String> loginLocal(@RestHeader(AUTHORIZATION_HEADER) String authHeader) {
        if (authHeader == null) {
            throw new UnauthorizedException();
        }
        if (!authHeader.startsWith(BASIC)) {
            throw new UnauthorizedException("Please use Basic authentication");
        }
        var basicCredential = authHeader.substring(BASIC_PREFIX_LENGTH);
        var decode = Base64.getDecoder().decode(basicCredential);
        var plainValue = new String(decode, Charset.defaultCharset());
        var colonPos = plainValue.indexOf(":");
        if (colonPos <= -1) {
            throw new UnauthorizedException("Please provide a valid Basic value");
        }
        var userName = plainValue.substring(0, colonPos);
        var password = plainValue.substring(colonPos + 1).toCharArray();
        UserEntity user;
        try {
            user = userRepository.getUserByName(userName);
            WildFlyElytronPasswordProvider provider = new WildFlyElytronPasswordProvider();
            PasswordFactory passwordFactory = PasswordFactory.getInstance(BCryptPassword.ALGORITHM_BCRYPT, provider);
            Password userPasswordDecoded = ModularCrypt.decode(user.passwordHash);
            Password userPasswordRestored = passwordFactory.translate(userPasswordDecoded);
            if (!passwordFactory.verify(userPasswordRestored, password)) {
                throw new UnauthorizedException();
            }
        } catch (UserNotFoundException e) {
            throw new UnauthorizedException();
        } catch (NoSuchAlgorithmException | InvalidKeyException | InvalidKeySpecException e) {
            e.printStackTrace();
            throw new InternalServerErrorException();
        }

        return getTokenResponseForUser(user);
    }


    @SecurityRequirement(
            name = "Google"
    )
    @POST()
    @Path("/google")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @PermitAll
    public RestResponse<String> loginGoogle(@RestHeader(AUTHORIZATION_HEADER) String authHeader) {
        if (authHeader == null) {
            throw new UnauthorizedException();
        }
        if (!authHeader.startsWith(BEARER)) {
            throw new UnauthorizedException("Please use Bearer authentication");
        }
        var bearerToken = authHeader.substring(BEARER_PREFIX_LENGTH);
        GoogleIdToken idToken = tokenValidationService.getGoogleIdToken(bearerToken);
        if (idToken == null) {
            throw new UnauthorizedException("Invalid google token");
        }
        UserEntity user;
        try {
            user = userRepository.getUserByExternalId(idToken.getPayload().getSubject());
        } catch (UserNotFoundException e) {
            throw new UnauthorizedException();
        }
        return getTokenResponseForUser(user);
    }

    private RestResponse<String> getTokenResponseForUser(UserEntity user) {
        var token = tokenService.getTokenForUser(user);
        var cookie = new NewCookie(
                "YodelToken",
                token,
                "/",
                "localhost",
                "auth",
                60 * 60 * 24 * 365 * 10,
                false,
                false
        );
        return RestResponse.ResponseBuilder
                .ok("Login succeeded!", MediaType.TEXT_PLAIN)
                .cookie(cookie)
                .build();
    }

    @POST()
    @Path("/facebook")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @PermitAll
    public RestResponse<String> loginFacebook(@RestHeader(AUTHORIZATION_HEADER) String authHeader) {
        if (authHeader == null) {
            throw new UnauthorizedException();
        }
        if (!authHeader.startsWith(BEARER)) {
            throw new UnauthorizedException("Please use Bearer authentication");
        }
        var bearerToken = authHeader.substring(BEARER_PREFIX_LENGTH);
        String id = tokenValidationService.getFacebookId(bearerToken);
        if (id == null) {
            throw new UnauthorizedException("Invalid facebook token");
        }
        UserEntity user;
        try {
            user = userRepository.getUserByExternalId(id);
        } catch (UserNotFoundException e) {
            throw new UnauthorizedException();
        }
        return getTokenResponseForUser(user);
    }
}
