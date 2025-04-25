package kr.mooner510.dsmpractice.domain.tag.controller

import io.swagger.v3.oas.annotations.Operation
import kr.mooner510.dsmpractice.domain.tag.data.dto.TagDto
import kr.mooner510.dsmpractice.domain.tag.service.TagService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/tag")
class TagController(private val tagService: TagService) {
    @Operation(summary = "모든 태그 목록 조회")
    @GetMapping("/list")
    fun getTags(): List<TagDto> {
        return tagService.getTags()
    }
}
