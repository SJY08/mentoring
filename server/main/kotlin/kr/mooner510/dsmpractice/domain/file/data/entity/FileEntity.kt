package kr.mooner510.dsmpractice.domain.file.data.entity

import jakarta.persistence.Basic
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.Lob
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "file")
class FileEntity(
    @Column(nullable = false, updatable = false, columnDefinition = "BINARY(16)")
    val userId: UUID,

    @Column(nullable = false, updatable = false)
    val title: String,

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(nullable = false, updatable = false)
    val content: ByteArray,

    @Id
    @Column(columnDefinition = "BINARY(16)")
    val id: UUID = UUID.randomUUID(),
)
