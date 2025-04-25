package kr.mooner510.dsmpractice.security.config

import com.fasterxml.jackson.databind.ObjectMapper
import kr.mooner510.dsmpractice.security.component.ExceptionFilter
import kr.mooner510.dsmpractice.security.component.JwtFilter
import kr.mooner510.dsmpractice.security.component.LoggingFilter
import kr.mooner510.dsmpractice.security.component.TokenProvider
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class FilterConfiguration(
    private val tokenProvider: TokenProvider,
    private val objectMapper: ObjectMapper,
) : SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {

    override fun configure(builder: HttpSecurity) {
        val loggingFilter = LoggingFilter()
        val jwtFilter = JwtFilter(tokenProvider)
        val exceptionFilter = ExceptionFilter(objectMapper)
        builder.addFilterBefore(loggingFilter, UsernamePasswordAuthenticationFilter::class.java)
        builder.addFilterBefore(jwtFilter, LoggingFilter::class.java)
        builder.addFilterBefore(exceptionFilter, JwtFilter::class.java)
    }
}
