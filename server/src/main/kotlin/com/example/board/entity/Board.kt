package com.example.board.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "boards")
data class Board(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val boardId: Long = 0,

    @Column(nullable = false)
    val title: String,

    @Column(columnDefinition = "TEXT")
    val content: String,

    @Column(nullable = false)
    val username: String,

    @Column(nullable = false)
    val date: LocalDateTime = LocalDateTime.now()
)
