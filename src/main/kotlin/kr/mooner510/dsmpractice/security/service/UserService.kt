package kr.mooner510.dsmpractice.security.service

import kr.mooner510.dsmpractice.domain.file.service.FileService
import kr.mooner510.dsmpractice.security.data.entity.user.User
import kr.mooner510.dsmpractice.security.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class UserService(
    private val userRepository: UserRepository,
    private val fileService: FileService,
) {
    fun updateThumbnail(user: User, file: MultipartFile) {
        val uploadFile = fileService.uploadFile(user, file)
        userRepository.updateThumbnailById(uploadFile.id, user.id)
    }
}
