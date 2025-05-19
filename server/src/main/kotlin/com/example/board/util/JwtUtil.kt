package com.example.board.util

import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

@Component
class JwtUtil {
    @Value("\${jwt.secret}")
    private lateinit var secret: String

    @Value("\${jwt.expiration}")
    private var expiration: Long = 0

    fun generateToken(userId: String): String {
        val now = Date()
        val expiryDate = Date(now.time + expiration)
        return Jwts.builder()
            .setSubject(userId)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(Keys.hmacShaKeyFor(secret.toByteArray()), SignatureAlgorithm.HS512)
            .compact()
    }

    private fun getAllClaimsFromToken(token: String): Claims =
        Jwts.parser()
            .verifyWith(Keys.hmacShaKeyFor(secret.toByteArray()))
            .build()
            .parseSignedClaims(token)
            .payload


    private fun isTokenExpired(token: String): Boolean {
        val expirationDate: Date = getAllClaimsFromToken(token).expiration
        return expirationDate.before(Date())
    }

    fun validateToken(token: String, userDetails: UserDetails): Boolean {
        return try {
            val username = getAllClaimsFromToken(token).subject
            (username == userDetails.username) && !isTokenExpired(token)
        } catch (ex: JwtException) {
            false
        } catch (ex: IllegalArgumentException) {
            false
        }
    }

    fun getUserIdFromToken(token: String): String =
        getAllClaimsFromToken(token).subject
}
