package shop.tsrecipe.member.exception

class ErrorResponse(
    val code: String?,
    val message: String,
    val timestamp: String,
    ) {

    constructor(e: BaseException) : this(
        code = e.code,
        message = e.message,
        timestamp = e.timeStamp
    )
}