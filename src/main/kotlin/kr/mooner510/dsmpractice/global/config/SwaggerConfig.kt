package kr.mooner510.dsmpractice.global.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.security.SecurityScheme
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
@OpenAPIDefinition(
    info = Info(title = "DSM-Practice API", version = "1.0", description = "신나는 코딩의 삶"),
    security = [SecurityRequirement(name = "Bearer Authentication")]
)
@SecurityScheme(
    name = "Bearer Authentication",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT"
)
class SwaggerConfig {
    @Bean
    fun auth(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("인증")
            .pathsToMatch("/api/auth/**")
            .build()
    }

    @Bean
    fun page(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("페이지")
            .pathsToMatch("/api/page/**")
            .build()
    }

    @Bean
    fun user(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("유저")
            .pathsToMatch("/api/user/**")
            .build()
    }

    @Bean
    fun file(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("파일")
            .pathsToMatch("/api/image/**")
            .build()
    }

    @Bean
    fun tag(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("태그")
            .pathsToMatch("/api/tag/**")
            .build()
    }
}
