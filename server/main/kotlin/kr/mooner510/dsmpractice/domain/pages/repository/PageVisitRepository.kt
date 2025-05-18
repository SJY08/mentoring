package kr.mooner510.dsmpractice.domain.pages.repository

import kr.mooner510.dsmpractice.domain.pages.data.entity.PageContent
import kr.mooner510.dsmpractice.domain.pages.data.entity.PageVisit
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional

interface PageVisitRepository : JpaRepository<PageVisit, PageContent> {
    @Transactional
    @Modifying
    @Query("update PageVisit p set p.page = ?1 where p.visits = p.visits + 1")
    fun visitPage(page: PageContent)
}
