package com.example.board.controller

import com.example.board.dto.BoardRequest
import com.example.board.dto.BoardResponse
import com.example.board.service.BoardService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/boards")
class BoardController(private val boardService: BoardService) {

    @PostMapping
    fun create(@RequestHeader("Authorization") token: String,
               @RequestBody req: BoardRequest): ResponseEntity<BoardResponse> {
        // JwtAuthenticationFilter로부터 SecurityContext에 username을 설정했다고 가정
        val username = /* SecurityContextHolder.getContext().authentication.name */ "mockUser"
        val res = boardService.createBoard(username, req)
        return ResponseEntity.ok(res)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long,
               @RequestBody req: BoardRequest): ResponseEntity<BoardResponse> {
        val res = boardService.updateBoard(id, req)
        return ResponseEntity.ok(res)
    }

    @GetMapping
    fun getAll(): ResponseEntity<List<BoardResponse>> =
        ResponseEntity.ok(boardService.getAllBoards())

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Long): ResponseEntity<BoardResponse> =
        ResponseEntity.ok(boardService.getBoardById(id))

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> {
        boardService.deleteBoard(id)
        return ResponseEntity.noContent().build()
    }
}
