package de.swa;

import org.eclipse.microprofile.openapi.annotations.Components;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeIn;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.extensions.Extension;
import org.eclipse.microprofile.openapi.annotations.extensions.Extensions;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlow;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlows;
import org.eclipse.microprofile.openapi.annotations.security.OAuthScope;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;

import javax.ws.rs.core.Application;

@OpenAPIDefinition(
        info = @Info(title="Yodel API", version = "0.0.1"),
        components = @Components(
                securitySchemes = {
                        @SecurityScheme(
                                securitySchemeName = "Local",
                                type = SecuritySchemeType.HTTP,
                                scheme = "basic"
                        ),
                        @SecurityScheme(
                                securitySchemeName = "Google",
                                type = SecuritySchemeType.OAUTH2,
                                flows = @OAuthFlows(
                                        authorizationCode = @OAuthFlow(
                                                authorizationUrl = "https://accounts.google.com/o/oauth2/auth",
                                                tokenUrl = "https://oauth2.googleapis.com/token",
                                                scopes = {
                                                        @OAuthScope(
                                                                name = "openid"
                                                        ),
                                                        @OAuthScope(
                                                                name = "profile"
                                                        ),
                                                        @OAuthScope(
                                                                name = "email"
                                                        )
                                                }
                                        )
                                )
                        ),
                        @SecurityScheme(
                                securitySchemeName = "CookieAuth",
                                type = SecuritySchemeType.APIKEY,
                                in = SecuritySchemeIn.COOKIE
                        )
                }
        )
)
public class SwaggerUI extends Application {
}
