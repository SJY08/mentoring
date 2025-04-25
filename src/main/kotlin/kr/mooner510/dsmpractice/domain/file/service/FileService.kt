package kr.mooner510.dsmpractice.domain.file.service

import jakarta.transaction.Transactional
import kr.mooner510.dsmpractice.domain.file.data.entity.FileEntity
import kr.mooner510.dsmpractice.domain.file.repository.FileEntityRepository
import kr.mooner510.dsmpractice.global.error.ErrorCode
import kr.mooner510.dsmpractice.global.error.data.GlobalError
import kr.mooner510.dsmpractice.security.data.entity.user.User
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Service
class FileService(private val fileEntityRepository: FileEntityRepository) {
    fun getFile(id: UUID): FileEntity {
        return fileEntityRepository.findByIdOrNull(id) ?: throw GlobalError(ErrorCode.FILE_NOT_FOUND)
    }

    fun uploadFile(user: User, file: MultipartFile): FileEntity {
        return fileEntityRepository.save(FileEntity(user.id, file.name, file.bytes))
    }

    @Transactional
    fun deleteFile(id: UUID) {
        fileEntityRepository.deleteById(id)
    }
}
