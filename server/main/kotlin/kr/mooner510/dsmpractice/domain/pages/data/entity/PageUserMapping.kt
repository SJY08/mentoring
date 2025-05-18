package kr.mooner510.dsmpractice.domain.pages.data.entity

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import kr.mooner510.dsmpractice.security.data.entity.user.User

@Entity
@Table(
    name = "page_user_mapping", uniqueConstraints = [
        UniqueConstraint(name = "uc_pageusermapping_user_id", columnNames = ["user_id", "page_id"])
    ]
)
class PageUserMapping(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    val user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "page_id", nullable = false, updatable = false, columnDefinition = "BINARY(16)")
    val page: PageContent,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = -1
}
