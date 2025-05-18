package kr.mooner510.dsmpractice.domain.tag.repository

import kr.mooner510.dsmpractice.domain.tag.data.entity.Tag
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface TagRepository : JpaRepository<Tag, Long> {
    fun findByContent(content: String): Optional<Tag>

    fun findByContentIn(content: List<String>): List<Tag>
}
