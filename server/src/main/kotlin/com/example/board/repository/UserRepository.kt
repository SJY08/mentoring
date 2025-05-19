package com.example.board.repository

import com.example.board.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UserRepository : JpaRepository<User, Long> {
    fun findByUserId(userId: String): Optional<User>
}
