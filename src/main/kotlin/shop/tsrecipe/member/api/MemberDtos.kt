package shop.tsrecipe.member.api

import io.swagger.v3.oas.annotations.media.Schema
import org.bson.types.ObjectId
import shop.tsrecipe.member.domain.OAuthInfo
import shop.tsrecipe.member.domain.OAuthProvider
import shop.tsrecipe.member.exception.BaseException
import shop.tsrecipe.member.exception.ErrorCode
import shop.tsrecipe.member.service.GetMemberQuery
import shop.tsrecipe.member.service.SignUpCommand
import shop.tsrecipe.member.service.MemberUpdateCommand

@Schema(description = "회원 가입 RequestDTO")
data class SignUpRequest(
    @field:Schema(description = "OAuth Provider (GOOGLE, APPLE)")
    val oauthProvider: OAuthProvider,

    @field:Schema(description = "OAuth ID")
    val oauthId: String,

    @field:Schema(description = "닉네임")
    val nickname: String
) {
    fun toCommand(): SignUpCommand {
        return SignUpCommand(
            oauthInfo = OAuthInfo(this.oauthProvider, this.oauthId),
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

    @field:Schema(description = "닉네임")
    val nickname: String,

    @field:Schema(description = "인증 여부")
    val isVerified: Boolean
)

@Schema(
    description = """
    ### Member 단건 조회 RequestDTO
    
    하위 두 조합 중 하나를 필수로 만족해야 합니다.
    - memberId
    - (oauthProvider, oauthId)
"""
)
data class GetMemberRequest(
    @field:Schema(description = "Member ID")
    val memberId: String?,

    @field:Schema(description = "OAuth Provider (GOOGLE, APPLE)")
    val oauthProvider: OAuthProvider?,

    @field:Schema(description = "OAuth ID")
    val oauthId: String?
) {
    fun validate(): GetMemberRequest {
        val hasMemberId = !memberId.isNullOrBlank()
        val hasOAuthProvider = oauthProvider != null
        val hasOAuthId = !oauthId.isNullOrBlank()

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
            if (this.oauthProvider == null || oauthId == null) null
            else OAuthInfo(this.oauthProvider, this.oauthId)

        return GetMemberQuery(
            memberId = this.memberId?.let { ObjectId(it) },
            oauthInfo = oauthInfo
        )
    }
}

@Schema(description = "회원 정보 수정 RequestDTO")
data class MemberUpdateRequest(
    @field:Schema(description = "Member ID")
    val id: String,

    @field:Schema(description = "닉네임")
    val nickname: String
) {
    fun toCommand(): MemberUpdateCommand {
        return MemberUpdateCommand(
            id = ObjectId(this.id), nickname = this.nickname
        )
    }
}