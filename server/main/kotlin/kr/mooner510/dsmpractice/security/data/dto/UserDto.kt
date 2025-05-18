package kr.mooner510.dsmpractice.security.data.dto

import java.util.*

data class UserDto(
    val id: UUID,
    val name: String,
    val thumbnailURL: String?,
)
