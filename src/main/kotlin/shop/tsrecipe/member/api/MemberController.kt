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
        - (oauthProvider, oauthId)
        """
    )
    @GetMapping
    suspend fun getMember(
        @RequestParam(required = false) memberId: String?,
        @RequestParam(required = false) oauthProvider: OAuthProvider?,
        @RequestParam(required = false) oauthId: String?
    ): ResponseEntity<MemberResponse> {
        val request = GetMemberRequest(memberId, oauthProvider, oauthId).validate()

        return baseResponse(
            body = memberService.getMember(request.toQuery()).toResponse()
        )
    }

    @Operation(
        summary = "랜덤 닉네임 조회",
        description = "랜덤한 조합의 닉네임을 반환합니다."
    )
    @GetMapping("/nickname")
    suspend fun getRandomNickname(): ResponseEntity<String> {
        return baseResponse(
            body = memberService.getNicknameByRandom()
        )
    }
}