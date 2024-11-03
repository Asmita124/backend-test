package com.wallapop.marsRover.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

const val SECURITY_SCHEME_NAME = "bearerAuth"

@Configuration
class OpenApiConfig {
  @Bean
  fun openApi(): OpenAPI = OpenAPI()
    .info(
      Info()
        .title("Mars Rover Api")
        .description(
          "move and status of the rover"
        )
        .version("1.7.0")
    )
    .addSecurityItem(SecurityRequirement().addList(SECURITY_SCHEME_NAME))
    .components(
      Components()
        .addSecuritySchemes(
          SECURITY_SCHEME_NAME,
          SecurityScheme()
            .name(SECURITY_SCHEME_NAME)
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT")
        )
    )
    .addServersItem(Server().url("/"))
}
