package kr.mooner510.dsmpractice.security.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class CORSConfig : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins(
                "http://localhost:3000",
                "http://localhost",
                "https://dsm-api.mooner510.kr",
                "https://dsm-practice.vercel.app",
                "https://decord-alpha.vercel.app"
            )
            .allowedMethods("*")
    }
}
