package shop.tsrecipe.member.api

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import shop.tsrecipe.member.domain.OAuthProvider
import shop.tsrecipe.member.domain.toResponse
import shop.tsrecipe.member.service.MemberService
import shop.tsrecipe.member.util.baseResponse

@Tag(name = "Member", description = "Member APIs")
@RestController
class MemberController(
    private val memberService: MemberService
) {
    @Operation(
        summary = "회원 가입",
        description = "신규 Member 등록 API"
    )
    @PostMapping
    suspend fun signUp(@RequestBody request: SignUpRequest): ResponseEntity<MemberResponse> {
        return baseResponse(
            body = memberService.signUp(request.toCommand()).toResponse()
        )
    }

    @Operation(
        summary = "Member 단건 조회",
        description = """
        Member 단건 조회
    
        하위 두 조합 중 하나를 필수로 만족해야 합니다.
        - memberId
        - (oAuthProvider, oAuthId)
        """
    )
    @GetMapping
    suspend fun getMember(
        @RequestParam(required = false) memberId: String?,
        @RequestParam(required = false) oAuthProvider: OAuthProvider?,
        @RequestParam(required = false) oAuthId: String?
    ): ResponseEntity<MemberResponse> {
        val request = GetMemberRequest(memberId, oAuthProvider, oAuthId).validate()

        return baseResponse(
            body = memberService.getMember(request.toQuery()).toResponse()
        )
    }
}