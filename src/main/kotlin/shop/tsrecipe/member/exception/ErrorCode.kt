package shop.tsrecipe.member.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(val status: HttpStatus? = HttpStatus.NOT_FOUND, val message: String) {
    UNDEFINED_ERROR(status = HttpStatus.INTERNAL_SERVER_ERROR, message = "Sorry, undefined error."),
    DATABASE_ERROR(status = HttpStatus.INTERNAL_SERVER_ERROR, message = "Database server error."),
    BAD_REQUEST(status = HttpStatus.BAD_REQUEST, message = "Bad request. check api documents."),

    // member
    MEMBER_NOT_FOUND(message = "Member not found."),
    MEMBER_DUPLICATED(status = HttpStatus.CONFLICT, message = "Is already exist oauth info.")
    // external
}