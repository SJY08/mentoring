package kr.mooner510.dsmpractice.domain.pages.data.request

import kr.mooner510.dsmpractice.domain.pages.data.types.PageScope

data class PageContentRequest(
    val title: String,
    val content: String,
    val scope: PageScope,
    val tags: List<String>,
)
