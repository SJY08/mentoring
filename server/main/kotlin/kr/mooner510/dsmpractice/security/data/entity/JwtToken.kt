package kr.mooner510.dsmpractice.security.data.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import kr.mooner510.dsmpractice.global.data.entity.BaseEntityCreateOnly

@Entity
@Table(name = "jwt_token")
class JwtToken(
    @Id
    val token: String,
) : BaseEntityCreateOnly()
