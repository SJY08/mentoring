package kr.mooner510.dsmpractice.domain.pages.data.types

import org.springframework.data.domain.Sort
import org.springframework.data.domain.Sort.Direction
import org.springframework.data.domain.Sort.by

enum class PageSort {
    CREATED_AT_ASC,
    CREATED_AT_DESC,
    UPDATED_AT_ASC,
    UPDATED_AT_DESC,
    TITLE_ASC,
    TITLE_DESC;

    fun toSort(): Sort {
        return when (this) {
            CREATED_AT_ASC -> by(Direction.ASC, "createdAt")
            CREATED_AT_DESC -> by(Direction.DESC, "createdAt")
            UPDATED_AT_ASC -> by(Direction.ASC, "updatedAt")
            UPDATED_AT_DESC -> by(Direction.DESC, "updatedAt")
            TITLE_ASC -> by(Direction.ASC, "title")
            TITLE_DESC -> by(Direction.DESC, "title")
        }
    }
}
