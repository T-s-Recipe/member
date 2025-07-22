package shop.tsrecipe.member.api

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import shop.tsrecipe.member.domain.toResponse
import shop.tsrecipe.member.service.MemberService
import shop.tsrecipe.member.util.baseResponse

@RestController
class MemberController(
    private val memberService: MemberService
) {
    @PostMapping
    suspend fun signUp(@RequestBody request: SignUpRequest): ResponseEntity<MemberResponse> {
        return baseResponse(
            body = memberService.signUp(request.toCommand()).toResponse()
        )
    }

    @GetMapping
    suspend fun getMember(request: GetMemberRequest): ResponseEntity<MemberResponse> {
        return baseResponse(
            body = memberService.getMember(request.toQuery()).toResponse()
        )
    }
}