package shop.tsrecipe.member.exception

import org.springframework.http.HttpStatus
import shop.tsrecipe.member.util.getCurrentTimestamp

class BaseException(
    val httpStatus: HttpStatus,
    val code: String? = null,
    override val message: String
) : RuntimeException() {

    val timeStamp = getCurrentTimestamp()

    constructor(e: ErrorCode) : this(
        httpStatus = e.status!!,
        code = e.name,
        message = e.message,
    )
}