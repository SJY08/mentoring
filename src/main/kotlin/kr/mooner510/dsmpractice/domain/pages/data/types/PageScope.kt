package kr.mooner510.dsmpractice.domain.pages.data.types

enum class PageScope {
    PRIVATE,
    PROTECTED,
    PUBLIC;

    companion object {
        private val map = mapOf(
            PRIVATE to listOf(PRIVATE, PROTECTED, PUBLIC),
            PROTECTED to listOf(PROTECTED, PUBLIC),
            PUBLIC to listOf(PUBLIC),
        )
    }

    fun getHigher(): List<PageScope> {
        return map[this] ?: listOf(PUBLIC)
    }
}
