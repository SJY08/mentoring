package kr.mooner510.dsmpractice.domain.file.repository

import kr.mooner510.dsmpractice.domain.file.data.entity.FileEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface FileEntityRepository : JpaRepository<FileEntity, UUID> {
}
