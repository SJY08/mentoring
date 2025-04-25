package kr.mooner510.dsmpractice.security.service

import kr.mooner510.dsmpractice.global.error.ErrorCode
import kr.mooner510.dsmpractice.global.error.data.GlobalError
import kr.mooner510.dsmpractice.security.component.TokenProvider
import kr.mooner510.dsmpractice.security.data.entity.JwtToken
import kr.mooner510.dsmpractice.security.data.entity.user.Student
import kr.mooner510.dsmpractice.security.data.request.LoginRequest
import kr.mooner510.dsmpractice.security.data.request.ReissueRequest
import kr.mooner510.dsmpractice.security.data.response.TokenResponse
import kr.mooner510.dsmpractice.security.data.response.TokenResponseAccessOnly
import kr.mooner510.dsmpractice.security.repository.JwtTokenRepository
import kr.mooner510.dsmpractice.security.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*
import kotlin.jvm.optionals.getOrNull

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val tokenProvider: TokenProvider, private val jwtTokenRepository: JwtTokenRepository,
) {
    fun login(req: LoginRequest): TokenResponse {
        val user = userRepository.findByLoginId(req.id).getOrNull() ?: throw GlobalError(ErrorCode.LOGIN_FAILED)
        if (passwordEncoder.matches(req.pw, user.password)) {
            val token = TokenResponse(
                tokenProvider.newAccess(user),
                tokenProvider.newRefresh(user)
            )
            jwtTokenRepository.save(JwtToken(token.refreshToken))
            return token
        }
        throw GlobalError(ErrorCode.LOGIN_FAILED)
    }

    fun signUp(req: LoginRequest) {
        if (userRepository.existsByLoginIdIgnoreCase(req.id)) throw GlobalError(ErrorCode.USER_ALREADY_EXISTS)
        if (req.id.length > 30) throw GlobalError(ErrorCode.USER_NAME_TOO_LONG)
        userRepository.save(
            Student(
                UUID.randomUUID(),
                req.id,
                passwordEncoder.encode(req.pw)
            )
        )
    }

    fun reissue(req: ReissueRequest): TokenResponseAccessOnly {
        if (jwtTokenRepository.existsById(req.token)) throw GlobalError(ErrorCode.EXPIRED_TOKEN)
        val user = tokenProvider.getUser(req.token, false)
        return TokenResponseAccessOnly(tokenProvider.newAccess(user))
    }
}
