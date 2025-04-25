package kr.mooner510.dsmpractice.domain.tag.data.dto

import java.io.Serializable

data class TagDto(
    val id: Long,
    val content: String,
) : Serializable
