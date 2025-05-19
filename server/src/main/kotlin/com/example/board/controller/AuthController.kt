package com.example.board.controller

import com.example.board.dto.AuthRequest
import com.example.board.dto.AuthResponse
import com.example.board.service.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(private val authService: AuthService) {

    @PostMapping("/register")
    fun register(@RequestParam username: String, @RequestBody req: AuthRequest): ResponseEntity<AuthResponse> {
        val res = authService.register(username, req)
        return ResponseEntity.ok(res)
    }

    @PostMapping("/login")
    fun login(@RequestBody req: AuthRequest): ResponseEntity<AuthResponse> {
        val res = authService.login(req)
        return ResponseEntity.ok(res)
    }
}
