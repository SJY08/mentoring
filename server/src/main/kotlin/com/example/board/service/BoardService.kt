package com.example.board.service

import com.example.board.dto.BoardRequest
import com.example.board.dto.BoardResponse
import com.example.board.entity.Board
import com.example.board.repository.BoardRepository
import org.springframework.stereotype.Service

@Service
class BoardService(private val boardRepository: BoardRepository) {
    fun createBoard(username: String, req: BoardRequest): BoardResponse {
        val board = boardRepository.save(Board(
            title = req.title,
            content = req.content,
            username = username
        ))
        return BoardResponse(board.boardId, board.title, board.content, board.username, board.date.toString())
    }

    fun updateBoard(id: Long, req: BoardRequest): BoardResponse {
        val board = boardRepository.findById(id)
            .orElseThrow { NoSuchElementException("게시글이 없습니다: $id") }
        val updated = board.copy(title = req.title, content = req.content)
        val saved = boardRepository.save(updated)
        return BoardResponse(saved.boardId, saved.title, saved.content, saved.username, saved.date.toString())
    }

    fun getAllBoards(): List<BoardResponse> =
        boardRepository.findAll().map {
            BoardResponse(it.boardId, it.title, it.content, it.username, it.date.toString())
        }

    fun getBoardById(id: Long): BoardResponse {
        val board = boardRepository.findById(id)
            .orElseThrow { NoSuchElementException("게시글이 없습니다: $id") }
        return BoardResponse(board.boardId, board.title, board.content, board.username, board.date.toString())
    }

    fun deleteBoard(id: Long) {
        if (!boardRepository.existsById(id)) {
            throw NoSuchElementException("게시글이 없습니다: $id")
        }
        boardRepository.deleteById(id)
    }
}
