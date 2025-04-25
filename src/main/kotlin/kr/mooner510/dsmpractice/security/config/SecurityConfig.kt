package kr.mooner510.dsmpractice.security.config

import com.fasterxml.jackson.databind.ObjectMapper
import kr.mooner510.dsmpractice.security.component.TokenProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsUtils

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val tokenProvider: TokenProvider,
    private val objectMapper: ObjectMapper,
) {
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    @Throws(Exception::class)
    fun configure(httpSecurity: HttpSecurity): SecurityFilterChain {
        return httpSecurity
            .csrf(CsrfConfigurer<HttpSecurity>::disable)
            .formLogin(FormLoginConfigurer<HttpSecurity>::disable)
            .logout(LogoutConfigurer<HttpSecurity>::disable)
            .cors { }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests {
                it.requestMatchers(CorsUtils::isPreFlightRequest).permitAll()

                it.requestMatchers(HttpMethod.GET, "/v1/**").permitAll()

                it.requestMatchers(HttpMethod.POST, "/api/auth/sign-in", "/api/auth/sign-up").permitAll()
                it.requestMatchers(HttpMethod.POST, "/api/auth/reissue").permitAll()

                it.requestMatchers(
                    HttpMethod.GET,
                    "/api/page",
                    "/api/page/{id}",
                    "/api/page/search",
                    "/api/page/user",
                    "/api/page/suggest"
                ).permitAll()
                it.requestMatchers(HttpMethod.POST, "/api/page").authenticated()
                it.requestMatchers(HttpMethod.PUT, "/api/page/{id}").authenticated()
                it.requestMatchers(HttpMethod.DELETE, "/api/page/{id}").authenticated()

                it.requestMatchers(HttpMethod.GET, "/api/tag/list").permitAll()

                it.requestMatchers(HttpMethod.POST, "/api/user/thumbnail").authenticated()
                it.requestMatchers(HttpMethod.GET, "/api/user/me").authenticated()
                it.requestMatchers(HttpMethod.GET, "/api/user/{id}").permitAll()

                it.requestMatchers(HttpMethod.GET, "/api/image/{id}").permitAll()

                it.anyRequest().denyAll()
            }
            .with(FilterConfiguration(tokenProvider, objectMapper), Customizer.withDefaults())
            .build()
    }
}
