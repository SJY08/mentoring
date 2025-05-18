package kr.mooner510.dsmpractice.global.error

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val message: String,
    val status: HttpStatus,
) {
    USER_NOT_FOUND("사용자를 찾지 못했습니다.", HttpStatus.NOT_FOUND),
    USER_ALREADY_EXISTS("이미 사용 중인 계정입니다.", HttpStatus.BAD_REQUEST),
    USER_NAME_TOO_LONG("이름이 너무 깁니다. (최대 30자)", HttpStatus.BAD_REQUEST),
    LOGIN_FAILED("아이디 혹은 비밀번호가 틀렸습니다.", HttpStatus.BAD_REQUEST),
    EXPIRED_TOKEN("로그인이 만료되었습니다.", HttpStatus.FORBIDDEN),
    UNSUPPORTED_TOKEN("잘못된 토큰입니다.", HttpStatus.FORBIDDEN),
    INVALID_UUID("잘못된 UUID입니다.", HttpStatus.BAD_REQUEST),

    PAGE_NOT_FOUND("없는 페이지입니다.", HttpStatus.NOT_FOUND),
    PAGE_FORBIDDEN("접근 권한이 없는 페이지입니다.", HttpStatus.NOT_FOUND),
    FILE_NOT_FOUND("파일을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
}
