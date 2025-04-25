package kr.mooner510.dsmpractice.utils

import kr.mooner510.dsmpractice.global.error.ErrorCode
import kr.mooner510.dsmpractice.global.error.data.GlobalError
import java.util.*

object UUIDParser {
    fun transfer(key: String): UUID {
        try {
            return UUID.fromString(key)
        } catch (e: Exception) {
            throw GlobalError(ErrorCode.INVALID_UUID)
        }
    }

    fun UUID.toURL(): String {
        return "/api/image/${this}"
    }
}
