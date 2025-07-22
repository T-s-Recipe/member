package shop.tsrecipe.member.api

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
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
           # Member 단건 조회 API
            - 파라미터 상세 조건 확인 필요 
        """
    )
    @GetMapping
    suspend fun getMember(request: GetMemberRequest): ResponseEntity<MemberResponse> {
        return baseResponse(
            body = memberService.getMember(request.toQuery()).toResponse()
        )
    }
}