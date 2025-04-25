package kr.mooner510.dsmpractice.domain.tag.service

import kr.mooner510.dsmpractice.domain.tag.data.dto.TagDto
import kr.mooner510.dsmpractice.domain.tag.data.entity.Tag
import kr.mooner510.dsmpractice.domain.tag.repository.TagRepository
import org.springframework.stereotype.Service

@Service
class TagService(private val tagRepository: TagRepository) {
    fun getTag(tag: String): Tag {
        return tagRepository.findByContent(tag).orElseGet { tagRepository.save(Tag(tag)) }
    }

    fun getTags(): List<TagDto> {
        return tagRepository.findAll().map { TagDto(it.id, it.content) }
    }
}
