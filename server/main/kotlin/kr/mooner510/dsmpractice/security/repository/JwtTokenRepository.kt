package kr.mooner510.dsmpractice.security.repository

import kr.mooner510.dsmpractice.security.data.entity.JwtToken
import org.springframework.data.jpa.repository.JpaRepository

interface JwtTokenRepository : JpaRepository<JwtToken, String> {
}
