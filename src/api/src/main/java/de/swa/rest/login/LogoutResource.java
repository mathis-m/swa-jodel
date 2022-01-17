package de.swa.rest.login;

import io.quarkus.security.Authenticated;
import org.jboss.resteasy.reactive.RestResponse;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;

@RequestScoped
@Path("/logout")
public class LogoutResource {
    @Authenticated
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public RestResponse<String> logout() {
        return unsetCookie();
    }

    private RestResponse<String> unsetCookie() {
        var cookie = new NewCookie(
                "YodelToken",
                "",
                "/",
                "localhost",
                "auth",
                0,
                false,
                false
        );
        return RestResponse.ResponseBuilder
                .ok("Logout succeeded!", MediaType.TEXT_PLAIN)
                .cookie(cookie)
                .build();
    }
}
