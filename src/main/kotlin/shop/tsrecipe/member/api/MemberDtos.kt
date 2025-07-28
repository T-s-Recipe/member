package shop.tsrecipe.member.api

import io.swagger.v3.oas.annotations.media.Schema
import org.bson.types.ObjectId
import shop.tsrecipe.member.domain.OAuthInfo
import shop.tsrecipe.member.domain.OAuthProvider
import shop.tsrecipe.member.exception.BaseException
import shop.tsrecipe.member.exception.ErrorCode
import shop.tsrecipe.member.service.GetMemberQuery
import shop.tsrecipe.member.service.SignUpCommand

@Schema(description = "회원 가입 RequestDTO")
data class SignUpRequest(
    @field:Schema(description = "OAuth Provider (GOOGLE, APPLE)")
    val oauthProvider: OAuthProvider,

    @field:Schema(description = "OAuth ID")
    val oauthId: String,

    @field:Schema(description = "이름")
    val name: String,

    @field:Schema(description = "이메일")
    val email: String?,

    @field:Schema(description = "닉네임")
    val nickname: String
) {
    fun toCommand(): SignUpCommand {
        return SignUpCommand(
            oAuthInfo = OAuthInfo(this.oauthProvider, this.oauthId),
            name = this.name,
            email = this.email,
            nickname = this.nickname,
        )
    }
}

@Schema(description = "Member ResponseDTO")
data class MemberResponse(
    @field:Schema(description = "Member ID")
    val id: String,

    @field:Schema(description = "OAuth Provider (GOOGLE, APPLE)")
    val oauthProvider: OAuthProvider,

    @field:Schema(description = "OAuth ID")
    val oauthId: String,

    @field:Schema(description = "이름")
    val name: String,

    @field:Schema(description = "이메일")
    val email: String?,

    @field:Schema(description = "닉네임")
    val nickname: String
)

@Schema(
    description = """
    ### Member 단건 조회 RequestDTO
    
    하위 두 조합 중 하나를 필수로 만족해야 합니다.
    - memberId
    - (oAuthProvider, oAuthId)
"""
)
data class GetMemberRequest(
    @field:Schema(description = "Member ID")
    val memberId: String?,

    @field:Schema(description = "OAuth Provider (GOOGLE, APPLE)")
    val oAuthProvider: OAuthProvider?,

    @field:Schema(description = "OAuth ID")
    val oAuthId: String?
) {
    fun validate(): GetMemberRequest {
        val hasMemberId = !memberId.isNullOrBlank()
        val hasOAuthProvider = oAuthProvider != null
        val hasOAuthId = !oAuthId.isNullOrBlank()

        if (!hasMemberId && !(hasOAuthProvider && hasOAuthId)) {
            throw BaseException(ErrorCode.BAD_REQUEST)
        }

        if (hasOAuthProvider != hasOAuthId) {
            throw BaseException(ErrorCode.BAD_REQUEST)
        }
        return this
    }

    fun toQuery(): GetMemberQuery {
        val oauthInfo =
            if (this.oAuthProvider == null || oAuthId == null) null
            else OAuthInfo(this.oAuthProvider, this.oAuthId)

        return GetMemberQuery(
            memberId = this.memberId?.let { ObjectId(it) },
            oauthInfo = oauthInfo
        )
    }
}