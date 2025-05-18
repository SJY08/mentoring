package kr.mooner510.dsmpractice.security.data.response

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String,
)
