package kr.mooner510.dsmpractice.domain.pages.data.dto

import kr.mooner510.dsmpractice.domain.pages.data.types.PageScope
import kr.mooner510.dsmpractice.security.data.dto.UserDto
import java.util.*

data class PageContentDto(
    val id: UUID,
    val user: UserDto,
    val title: String,
    val content: String,
    val scope: PageScope,
    val tags: List<String>,
    val thumbnailURL: String?,
)
