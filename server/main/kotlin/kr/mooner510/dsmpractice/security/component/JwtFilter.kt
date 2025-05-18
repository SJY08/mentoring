package kr.mooner510.dsmpractice.security.component

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kr.mooner510.dsmpractice.global.error.ErrorCode
import kr.mooner510.dsmpractice.global.error.data.GlobalError
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JwtFilter(
    private val tokenProvider: TokenProvider,
) : OncePerRequestFilter() {

    companion object {
        const val AUTH = "Authorization"
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val accessToken = getToken(request)
        accessToken?.let {
            val user = tokenProvider.getUser(it, true)
            SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(user, it, user.authorities)
        }
        filterChain.doFilter(request, response)
    }

    private fun getToken(request: HttpServletRequest): String? {
        val header = request.getHeader(AUTH) ?: return null
        if (header.startsWith("Bearer ")) return header.substring(7)
        throw GlobalError(ErrorCode.UNSUPPORTED_TOKEN)
    }
}
