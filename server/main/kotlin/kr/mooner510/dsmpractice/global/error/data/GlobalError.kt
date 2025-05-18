package kr.mooner510.dsmpractice.global.error.data

import kr.mooner510.dsmpractice.global.error.ErrorCode

open class GlobalError(
    val errorCode: ErrorCode,
) : RuntimeException(errorCode.message)
