package shop.tsrecipe.member.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import shop.tsrecipe.member.util.baseResponse

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(Exception::class)
    fun undefinedException(e: Exception): ResponseEntity<ErrorResponse> {
        return baseResponse(
            status = HttpStatus.INTERNAL_SERVER_ERROR,
            body = ErrorResponse(BaseException(ErrorCode.UNDEFINED_ERROR))
        )
    }

    @ExceptionHandler(BaseException::class)
    fun handleBaseException(e: BaseException): ResponseEntity<ErrorResponse> {
        return baseResponse(e.httpStatus, ErrorResponse(e))
    }
}