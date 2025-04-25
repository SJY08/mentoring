package kr.mooner510.dsmpractice.security.controller

import io.swagger.v3.oas.annotations.Operation
import kr.mooner510.dsmpractice.security.data.request.LoginRequest
import kr.mooner510.dsmpractice.security.data.request.ReissueRequest
import kr.mooner510.dsmpractice.security.data.response.TokenResponse
import kr.mooner510.dsmpractice.security.data.response.TokenResponseAccessOnly
import kr.mooner510.dsmpractice.security.service.AuthService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService,
) {
    @Operation(summary = "로그인")
    @PostMapping("sign-in")
    fun login(
        @RequestBody req: LoginRequest,
    ): TokenResponse {
        return authService.login(req)
    }

    @Operation(summary = "회원가입")
    @PostMapping("sign-up")
    fun signup(
        @RequestBody req: LoginRequest,
    ) {
        authService.signUp(req)
    }

    @Operation(summary = "토큰 재발급")
    @PostMapping("reissue")
    fun reissue(
        @RequestBody req: ReissueRequest,
    ): TokenResponseAccessOnly {
        return authService.reissue(req)
    }
}
