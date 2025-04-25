package kr.mooner510.dsmpractice.domain.pages.controller

import io.swagger.v3.oas.annotations.Operation
import kr.mooner510.dsmpractice.domain.pages.data.dto.PageContentDto
import kr.mooner510.dsmpractice.domain.pages.data.request.PageContentRequest
import kr.mooner510.dsmpractice.domain.pages.data.types.PageScope
import kr.mooner510.dsmpractice.domain.pages.data.types.PageSort
import kr.mooner510.dsmpractice.domain.pages.service.PageService
import kr.mooner510.dsmpractice.security.data.entity.user.User
import kr.mooner510.dsmpractice.utils.UUIDParser
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.MediaType
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/page")
class PageController(
    private val pageService: PageService,
) {

    @Operation(summary = "추천 페이지")
    @GetMapping("/suggest")
    fun getSuggestPage(
        @RequestParam count: Int,
    ): List<PageContentDto> {
        return pageService.getSuggestPage(2.5, count)
    }

    @Operation(summary = "특정 페이지 조회")
    @GetMapping("/{id}")
    fun getPage(
        @AuthenticationPrincipal user: User?,
        @PathVariable id: String,
    ): PageContentDto {
        return pageService.getPage(user, UUIDParser.transfer(id))
    }

    @Operation(
        summary = "나의 페이지 검색",
        description = "tagIds, tagNames중 하나만 입력하면 됩니다.<br/>우선 순위: tagIds > tagNames"
    )
    @GetMapping("/me")
    fun getMyPages(
        @AuthenticationPrincipal user: User,
        @RequestParam(required = false, defaultValue = "1") page: Int,
        @RequestParam(required = false, defaultValue = "10") size: Int,
        @RequestParam(required = false) content: String?,
        @RequestParam(required = false) tagNames: List<String>?,
        @RequestParam(required = false) tagIds: List<Long>?,
        @RequestParam(required = false, defaultValue = "PUBLIC") scope: PageScope,
        @RequestParam(required = false, defaultValue = "CREATED_AT_DESC") sort: PageSort,
    ): Page<PageContentDto> {
        return pageService.getPages(
            user.id,
            content,
            tagNames,
            tagIds,
            scope,
            PageRequest.of(page, size, sort.toSort())
        )
    }

    @Operation(
        summary = "모든 페이지 검색",
        description = "tagIds, tagNames중 하나만 입력하면 됩니다.<br/>우선 순위: tagIds > tagNames"
    )
    @GetMapping("/search")
    fun getPages(
        @RequestParam(required = false, defaultValue = "1") page: Int,
        @RequestParam(required = false, defaultValue = "10") size: Int,
        @RequestParam(required = false) content: String?,
        @RequestParam(required = false) tagNames: List<String>?,
        @RequestParam(required = false) tagIds: List<Long>?,
        @RequestParam(required = false, defaultValue = "CREATED_AT_DESC") sort: PageSort,
    ): Page<PageContentDto> {
        return pageService.getPages(
            null,
            content,
            tagNames,
            tagIds,
            PageScope.PUBLIC,
            PageRequest.of(page, size, sort.toSort())
        )
    }

    @Operation(
        summary = "특정 유저의 페이지 검색",
        description = "tagIds, tagNames중 하나만 입력하면 됩니다.<br/>우선 순위: tagIds > tagNames"
    )
    @GetMapping("/user")
    fun getPages(
        @RequestParam userId: String,
        @RequestParam(required = false, defaultValue = "1") page: Int,
        @RequestParam(required = false, defaultValue = "10") size: Int,
        @RequestParam(required = false) content: String?,
        @RequestParam(required = false) tagNames: List<String>?,
        @RequestParam(required = false) tagIds: List<Long>?,
        @RequestParam(required = false, defaultValue = "CREATED_AT_DESC") sort: PageSort,
    ): Page<PageContentDto> {
        return pageService.getPages(
            UUIDParser.transfer(userId),
            content,
            tagNames,
            tagIds,
            PageScope.PUBLIC,
            PageRequest.of(page, size, sort.toSort())
        )
    }

    @Operation(summary = "페이지 추가")
    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE])
    fun createPage(
        @AuthenticationPrincipal user: User,
        @RequestPart body: PageContentRequest,
        @RequestPart(required = false) file: MultipartFile?,
    ): PageContentDto {
        return pageService.createPage(user, body, file)
    }

    @Operation(summary = "페이지 수정")
    @PutMapping("/{id}", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE])
    fun updatePage(
        @AuthenticationPrincipal user: User,
        @PathVariable id: String,
        @RequestPart body: PageContentRequest,
        @RequestPart(required = false) file: MultipartFile?,
    ): PageContentDto {
        return pageService.updatePage(user, UUIDParser.transfer(id), body, file)
    }

    @Operation(summary = "페이지 삭제")
    @DeleteMapping("/{id}")
    fun deletePage(
        @AuthenticationPrincipal user: User,
        @PathVariable id: String,
    ) {
        pageService.deletePage(user, UUIDParser.transfer(id))
    }

}
