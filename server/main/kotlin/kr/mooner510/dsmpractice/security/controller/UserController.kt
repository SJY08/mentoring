package kr.mooner510.dsmpractice.security.controller

import io.swagger.v3.oas.annotations.Operation
import kr.mooner510.dsmpractice.global.error.ErrorCode
import kr.mooner510.dsmpractice.global.error.data.GlobalError
import kr.mooner510.dsmpractice.security.data.dto.UserDto
import kr.mooner510.dsmpractice.security.data.entity.user.User
import kr.mooner510.dsmpractice.security.repository.UserRepository
import kr.mooner510.dsmpractice.security.service.UserService
import kr.mooner510.dsmpractice.utils.UUIDParser
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.MediaType
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/user")
class UserController(
    private val userService: UserService,
    private val userRepository: UserRepository,
) {
    @Operation(summary = "유저 썸네일 업로드")
    @PostMapping("/thumbnail", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE])
    fun updateThumbnail(
        @AuthenticationPrincipal user: User,
        @RequestPart file: MultipartFile,
    ) {
        return userService.updateThumbnail(user, file)
    }

    @Operation(summary = "내 데이터")
    @GetMapping("/me")
    fun getMe(@AuthenticationPrincipal user: User): UserDto {
        return user.toDto()
    }

    @Operation(summary = "쟤 데이터")
    @GetMapping("/{id}")
    fun getUser(@PathVariable id: String): UserDto {
        return userRepository.findByIdOrNull(UUIDParser.transfer(id))?.toDto() ?: throw GlobalError(ErrorCode.USER_NOT_FOUND)
    }
}
