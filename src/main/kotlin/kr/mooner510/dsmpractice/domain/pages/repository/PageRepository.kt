package kr.mooner510.dsmpractice.domain.pages.repository

import kr.mooner510.dsmpractice.domain.pages.data.entity.PageContent
import kr.mooner510.dsmpractice.domain.pages.data.types.PageScope
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface PageRepository : JpaRepository<PageContent, UUID> {
    @Query(
        """
        select p
        from PageContent p
        where p.scope in :scopes
        """
    )
    fun findPages(scopes: List<PageScope>, pageable: Pageable): Page<PageContent>

    @Query(
        """
        select p
        from PageContent p
            join PageTagMapping m
            on m.tag.id in :tagIds
        where p.scope in :scopes
        """
    )
    fun findPagesWithTags(tagIds: List<Long>, scopes: List<PageScope>, pageable: Pageable): Page<PageContent>

    @Query(
        """
        select p
        from PageContent p
        where p.scope in :scopes
            and lower(p.title) like concat('%', :content, '%')
            and lower(p.content) like concat('%', :content, '%')
        """
    )
    fun findPagesWithContent(scopes: List<PageScope>, content: String, pageable: Pageable): Page<PageContent>

    @Query(
        """
        select p
        from PageContent p
            join PageTagMapping m
            on m.tag.id in :tagIds
        where p.scope in :scopes
            and lower(p.title) like concat('%', :content, '%')
            and lower(p.content) like concat('%', :content, '%')
        """
    )
    fun findPagesWithContentAndTags(tagIds: List<Long>, scopes: List<PageScope>, content: String, pageable: Pageable): Page<PageContent>

    @Query(
        """
        select p
        from PageContent p
        where p.userId = :userId
            and p.scope in :scopes
        """
    )
    fun findUserPages(userId: UUID, scopes: List<PageScope>, pageable: Pageable): Page<PageContent>

    @Query(
        """
        select p
        from PageContent p
            join PageTagMapping m
            on m.tag.id in :tagIds
        where p.userId = :userId
            and p.scope in :scopes
        """
    )
    fun findUserPagesWithTags(userId: UUID, tagIds: List<Long>, scopes: List<PageScope>, pageable: Pageable): Page<PageContent>

    @Query(
        """
        select p
        from PageContent p
        where p.userId = :userId
            and p.scope in :scopes
            and lower(p.title) like concat('%', :content, '%')
            and lower(p.content) like concat('%', :content, '%')
        """
    )
    fun findUserPagesWithContent(userId: UUID, scopes: List<PageScope>, content: String, pageable: Pageable): Page<PageContent>

    @Query(
        """
        select p
        from PageContent p
            join PageTagMapping m
            on m.tag.id in :tagIds
        where p.userId = :userId
            and p.scope in :scopes
            and lower(p.title) like concat('%', :content, '%')
            and lower(p.content) like concat('%', :content, '%')
        """
    )
    fun findUserPagesWithContentAndTags(
        userId: UUID,
        tagIds: List<Long>,
        scopes: List<PageScope>,
        content: String,
        pageable: Pageable,
    ): Page<PageContent>

    @Query(
        value = """
            select * from (
                select * from page_content p
                left join (
                    select page_id, count(user_id) as likes
                    from page_user_mapping
                    group by page_id
                ) likes on p.id = likes.page_id
                left join (
                    select page_id as pageId, sum(visits) as visits
                    from page_visit
                    group by page_id
                ) visits on p.id = pageId
                where p.scope = 'PUBLIC'
                ORDER BY (COALESCE(likes.likes, 0) * 5 + COALESCE(visits.visits, 0)) DESC 
                LIMIT :first
            ) k
            order by rand()
            limit :last
        """,
        nativeQuery = true
    )
    fun randomPage(first: Int, last: Int): List<PageContent>
}
