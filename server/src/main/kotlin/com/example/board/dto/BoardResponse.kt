package com.example.board.dto

data class BoardResponse(
    val boardId: Long,
    val title: String,
    val content: String,
    val username: String,
    val date: String
)
