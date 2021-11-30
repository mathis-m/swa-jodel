package de.swa;

import org.eclipse.microprofile.openapi.annotations.Components;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.info.Info;
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
                        )
                }
        )
)
public class SwaggerUI extends Application {
}
