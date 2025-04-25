package kr.mooner510.dsmpractice.domain.pages.service

import jakarta.transaction.Transactional
import kr.mooner510.dsmpractice.domain.file.service.FileService
import kr.mooner510.dsmpractice.domain.pages.data.dto.PageContentDto
import kr.mooner510.dsmpractice.domain.pages.data.entity.PageContent
import kr.mooner510.dsmpractice.domain.pages.data.entity.PageTagMapping
import kr.mooner510.dsmpractice.domain.pages.data.request.PageContentRequest
import kr.mooner510.dsmpractice.domain.pages.data.types.PageScope
import kr.mooner510.dsmpractice.domain.pages.repository.PageRepository
import kr.mooner510.dsmpractice.domain.pages.repository.PageTagMappingRepository
import kr.mooner510.dsmpractice.domain.pages.repository.PageVisitRepository
import kr.mooner510.dsmpractice.domain.tag.repository.TagRepository
import kr.mooner510.dsmpractice.domain.tag.service.TagService
import kr.mooner510.dsmpractice.global.error.ErrorCode
import kr.mooner510.dsmpractice.global.error.data.GlobalError
import kr.mooner510.dsmpractice.security.data.entity.user.User
import kr.mooner510.dsmpractice.security.repository.UserRepository
import kr.mooner510.dsmpractice.utils.UUIDParser.toURL
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*
import kotlin.math.max

@Service
class PageService(
    private val pageRepository: PageRepository,
    private val userRepository: UserRepository,
    private val fileService: FileService,
    private val tagService: TagService,
    private val pageTagMappingRepository: PageTagMappingRepository,
    private val tagRepository: TagRepository,
    private val pageVisitRepository: PageVisitRepository,
) {
    fun getSuggestPage(searchDepth: Double, count: Int): List<PageContentDto> {
        return pageRepository.randomPage((count * max(1.0, searchDepth)).toInt(), count).map {
            PageContentDto(
                it.id,
                (userRepository.findByIdOrNull(it.userId) ?: throw GlobalError(ErrorCode.USER_NOT_FOUND)).toDto(),
                it.title,
                it.content,
                it.scope,
                pageTagMappingRepository.findByPageId(it.id),
                it.thumbnail?.toURL()
            )
        }
    }

    fun getPages(
        userId: UUID?,
        content: String?,
        tagNames: List<String>?,
        tagIds: List<Long>?,
        scope: PageScope,
        pageable: Pageable,
    ): Page<PageContentDto> {
        return if (userId == null) {
            val pages = if (!tagIds.isNullOrEmpty()) {
                if (!content.isNullOrEmpty()) {
                    pageRepository.findPagesWithContentAndTags(tagIds, scope.getHigher(), content, pageable)
                } else {
                    pageRepository.findPagesWithTags(tagIds, scope.getHigher(), pageable)
                }
            } else if (!tagNames.isNullOrEmpty()) {
                val ids = tagRepository.findByContentIn(tagNames).map { it.id }
                if (!content.isNullOrEmpty()) {
                    pageRepository.findPagesWithContentAndTags(ids, scope.getHigher(), content, pageable)
                } else {
                    pageRepository.findPagesWithTags(ids, scope.getHigher(), pageable)
                }
            } else {
                if (!content.isNullOrEmpty()) {
                    pageRepository.findPagesWithContent(scope.getHigher(), content, pageable)
                } else {
                    pageRepository.findPages(scope.getHigher(), pageable)
                }
            }
            pages(pages)
        } else {
            val pages = if (!tagIds.isNullOrEmpty()) {
                if (!content.isNullOrEmpty()) {
                    pageRepository.findUserPagesWithContentAndTags(userId, tagIds, scope.getHigher(), content, pageable)
                } else {
                    pageRepository.findUserPagesWithTags(userId, tagIds, scope.getHigher(), pageable)
                }
            } else if (!tagNames.isNullOrEmpty()) {
                val ids = tagRepository.findByContentIn(tagNames).map { it.id }
                if (!content.isNullOrEmpty()) {
                    pageRepository.findUserPagesWithContentAndTags(userId, ids, scope.getHigher(), content, pageable)
                } else {
                    pageRepository.findUserPagesWithTags(userId, ids, scope.getHigher(), pageable)
                }
            } else {
                if (!content.isNullOrEmpty()) {
                    pageRepository.findUserPagesWithContent(userId, scope.getHigher(), content, pageable)
                } else {
                    pageRepository.findUserPages(userId, scope.getHigher(), pageable)
                }
            }
            pages(userId, pages)
        }
    }

    private fun pages(userId: UUID, pages: Page<PageContent>): Page<PageContentDto> {
        if (pages.isEmpty) return Page.empty()

        val user = (userRepository.findByIdOrNull(userId) ?: throw GlobalError(ErrorCode.USER_NOT_FOUND)).toDto()
        return pages.map {
            PageContentDto(
                it.id,
                user,
                it.title,
                it.content,
                it.scope,
                pageTagMappingRepository.findByPageId(it.id),
                it.thumbnail?.toURL()
            )
        }
    }

    private fun pages(pages: Page<PageContent>): Page<PageContentDto> {
        if (pages.isEmpty) return Page.empty()

        return pages.map {
            PageContentDto(
                it.id,
                (userRepository.findByIdOrNull(it.userId) ?: throw GlobalError(ErrorCode.USER_NOT_FOUND)).toDto(),
                it.title,
                it.content,
                it.scope,
                pageTagMappingRepository.findByPageId(it.id),
                it.thumbnail?.toURL()
            )
        }
    }

    fun getPage(user: User?, id: UUID): PageContentDto {
        val pageContent = pageRepository.findByIdOrNull(id) ?: throw GlobalError(ErrorCode.PAGE_NOT_FOUND)
        if (pageContent.scope == PageScope.PROTECTED) {
            if (user == null) throw GlobalError(ErrorCode.PAGE_FORBIDDEN)
        } else if (pageContent.scope == PageScope.PRIVATE) {
            if (user == null || user.id != pageContent.userId) throw GlobalError(ErrorCode.PAGE_FORBIDDEN)
        }

        val tags = pageTagMappingRepository.findByPageId(pageContent.id)
        pageVisitRepository.visitPage(pageContent)

        return PageContentDto(
            pageContent.id,
            (userRepository.findByIdOrNull(pageContent.userId) ?: throw GlobalError(ErrorCode.USER_NOT_FOUND)).toDto(),
            pageContent.title,
            pageContent.content,
            pageContent.scope,
            tags,
            pageContent.thumbnail?.toURL()
        )
    }

    fun createPage(user: User, req: PageContentRequest, file: MultipartFile?): PageContentDto {
        val uploadFile = file?.let { fileService.uploadFile(user, file) }
        val pageContent = pageRepository.save(PageContent(user.id, req.title, req.content, req.scope, uploadFile?.id))

        updateTags(req.tags, pageContent)

        return PageContentDto(
            pageContent.id,
            (userRepository.findByIdOrNull(pageContent.userId) ?: throw GlobalError(ErrorCode.USER_NOT_FOUND)).toDto(),
            pageContent.title,
            pageContent.content,
            pageContent.scope,
            req.tags,
            pageContent.thumbnail?.toURL()
        )
    }

    fun updateTags(tags: Collection<String>, page: PageContent) {
        tags.toSet().forEach {
            val tag = tagService.getTag(it)
            pageTagMappingRepository.save(PageTagMapping(tag, page))
        }
    }

    fun updatePage(user: User, id: UUID, req: PageContentRequest, file: MultipartFile?): PageContentDto {
        var pageContent = pageRepository.findByIdOrNull(id) ?: throw GlobalError(ErrorCode.PAGE_NOT_FOUND)
        if (pageContent.userId != user.id) throw GlobalError(ErrorCode.PAGE_FORBIDDEN)

        pageContent.thumbnail?.let { fileService.deleteFile(it) }
        val uploadFile = file?.let { fileService.uploadFile(user, file) }

        pageTagMappingRepository.deleteByPageId(pageContent.id)
        updateTags(req.tags, pageContent)

        pageContent = pageRepository.save(PageContent(user.id, req.title, req.content, req.scope, uploadFile?.id, id))
        return PageContentDto(
            pageContent.id,
            (userRepository.findByIdOrNull(pageContent.userId) ?: throw GlobalError(ErrorCode.USER_NOT_FOUND)).toDto(),
            pageContent.title,
            pageContent.content,
            pageContent.scope,
            req.tags,
            pageContent.thumbnail?.toURL()
        )
    }

    @Transactional
    fun deletePage(user: User, id: UUID) {
        val pageContent = pageRepository.findByIdOrNull(id) ?: throw GlobalError(ErrorCode.PAGE_NOT_FOUND)
        if (pageContent.userId != user.id) throw GlobalError(ErrorCode.PAGE_FORBIDDEN)
        pageRepository.delete(pageContent)
    }
}
