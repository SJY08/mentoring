package kr.mooner510.dsmpractice.security.component

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.security.Keys
import kr.mooner510.dsmpractice.global.error.ErrorCode
import kr.mooner510.dsmpractice.global.error.data.GlobalError
import kr.mooner510.dsmpractice.security.data.entity.user.Admin
import kr.mooner510.dsmpractice.security.data.entity.user.Student
import kr.mooner510.dsmpractice.security.data.entity.user.User
import kr.mooner510.dsmpractice.security.data.types.UserType
import kr.mooner510.dsmpractice.utils.UUIDParser
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*

@Component
class TokenProvider(
    @Value("\${security.key}") private val keyString: String,
    @Value("\${security.access}") private val accessTime: Long,
    @Value("\${security.refresh}") private val refreshTime: Long,
) {
    private val key = Keys.hmacShaKeyFor(keyString.toByteArray())
    private val parser = Jwts.parser().verifyWith(key).build()

    fun newAccess(user: User): String {
        val now = Instant.now()

        return Jwts.builder()
            .id(user.id.toString())
            .subject("acc")
            .signWith(key)
            .claim("name", user.loginId)
            .claim("type", user.userType.toString())
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plusSeconds(accessTime)))
            .compact()
    }

    fun newRefresh(user: User): String {
        val now = Instant.now()

        return Jwts.builder()
            .id(user.id.toString())
            .subject("ref")
            .signWith(key)
            .claim("name", user.loginId)
            .claim("type", user.userType.toString())
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plusSeconds(refreshTime)))
            .compact()
    }

    fun getUser(token: String, isAccessToken: Boolean): User {
        try {
            val claims = parser.parseSignedClaims(token).payload
            val uuid = UUIDParser.transfer(claims.id)

            if (isAccessToken && claims.subject != "acc") throw GlobalError(ErrorCode.UNSUPPORTED_TOKEN)

            val loginId = claims.get("name", String::class.java)
            val userType = UserType.valueOf(claims.get("type", String::class.java))

            return when (userType) {
                UserType.Admin -> Admin(
                    uuid,
                    loginId,
                    ""
                )

                UserType.Student -> Student(
                    uuid,
                    loginId,
                    ""
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is UnsupportedJwtException) {
                throw GlobalError(ErrorCode.UNSUPPORTED_TOKEN)
            } else if (e is ExpiredJwtException) {
                throw GlobalError(ErrorCode.EXPIRED_TOKEN)
            }
            throw e
        }
    }
}
