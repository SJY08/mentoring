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
import kr.mooner510.dsmpractice.domain.tag.data.entity.Tag

@Entity
@Table(
    name = "page_tag_mapping", uniqueConstraints = [
        UniqueConstraint(name = "uc_tagpagemapping_tagid", columnNames = ["tagId", "pageId"])
    ]
)
class PageTagMapping(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id", nullable = false, updatable = false)
    val tag: Tag,


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "page_id", nullable = false, updatable = false, columnDefinition = "BINARY(16)")
    val page: PageContent,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = -1
}
