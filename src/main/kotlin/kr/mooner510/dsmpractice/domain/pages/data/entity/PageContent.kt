package kr.mooner510.dsmpractice.domain.pages.data.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.Table
import kr.mooner510.dsmpractice.domain.pages.data.types.PageScope
import kr.mooner510.dsmpractice.global.data.entity.BaseEntity
import java.util.*

@Entity
@Table(
    name = "PageContent", indexes = [
        Index(name = "idx_pagecontent_id", columnList = "id")
    ]
)
class PageContent(
    @Column(nullable = false, updatable = false, columnDefinition = "BINARY(16)")
    val userId: UUID,

    @Column(nullable = false)
    var title: String,

    @Column(nullable = false, length = 4095)
    var content: String,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var scope: PageScope,

    @Column(nullable = true, columnDefinition = "BINARY(16)")
    var thumbnail: UUID?,

    @Id
    val id: UUID = UUID.randomUUID(),
) : BaseEntity()
