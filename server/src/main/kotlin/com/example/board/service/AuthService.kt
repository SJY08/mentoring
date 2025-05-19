package com.example.board.service

import com.example.board.dto.AuthRequest
import com.example.board.dto.AuthResponse
import com.example.board.entity.User
import com.example.board.repository.UserRepository
import com.example.board.util.JwtUtil
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val jwtUtil: JwtUtil,
    private val passwordEncoder: BCryptPasswordEncoder = BCryptPasswordEncoder()
) {
    fun register(username: String, req: AuthRequest): AuthResponse {
        if (userRepository.findByUserId(req.userId).isPresent) {
            throw IllegalArgumentException("이미 존재하는 아이디입니다.")
        }
        val user = User(
            username = username,
            userId = req.userId,
            password = passwordEncoder.encode(req.password)
        )
        userRepository.save(user)
        val token = jwtUtil.generateToken(user.userId)
        return AuthResponse(token)
    }

    fun login(req: AuthRequest): AuthResponse {
        val user = userRepository.findByUserId(req.userId)
            .orElseThrow { IllegalArgumentException("존재하지 않는 사용자입니다.") }
        if (!passwordEncoder.matches(req.password, user.password)) {
            throw IllegalArgumentException("비밀번호가 일치하지 않습니다.")
        }
        val token = jwtUtil.generateToken(user.userId)
        return AuthResponse(token)
    }
}
