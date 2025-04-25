package kr.mooner510.dsmpractice.domain.pages.data.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.MapsId
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(
    name = "page_visit"
)
class PageVisit(
    @Id
    @Column(name = "page_id", nullable = false, updatable = false, unique = true, columnDefinition = "BINARY(16)")
    val pageId: UUID,

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "page_id")
    val page: PageContent,

    var visits: Int,
)
