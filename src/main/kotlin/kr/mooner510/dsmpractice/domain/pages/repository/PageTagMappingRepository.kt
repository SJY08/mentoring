package kr.mooner510.dsmpractice.domain.pages.repository

import kr.mooner510.dsmpractice.domain.pages.data.entity.PageTagMapping
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface PageTagMappingRepository : JpaRepository<PageTagMapping, Long> {

    fun deleteByPageId(pageId: UUID)

    @Query("SELECT p.tag.content FROM PageTagMapping p where p.page.id = ?1")
    fun findByPageId(pageId: UUID): List<String>

}
