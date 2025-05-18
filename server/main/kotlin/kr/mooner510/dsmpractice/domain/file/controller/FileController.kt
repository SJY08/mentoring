package kr.mooner510.dsmpractice.domain.file.controller

import kr.mooner510.dsmpractice.domain.file.service.FileService
import kr.mooner510.dsmpractice.utils.UUIDParser
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/image")
class FileController(
    private val fileService: FileService,
) {
    @GetMapping("/{id}", produces = [MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE])
    fun image(@PathVariable id: String): ByteArray {
        return fileService.getFile(UUIDParser.transfer(id)).content
    }
}
