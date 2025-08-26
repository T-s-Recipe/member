package shop.tsrecipe.member.service

import org.bson.types.ObjectId
import shop.tsrecipe.member.domain.OAuthInfo

data class SignUpCommand(
    val oauthInfo: OAuthInfo,
    val nickname: String
)

data class GetMemberQuery(
    val memberId: ObjectId? = null,
    val oauthInfo: OAuthInfo? = null
)

data class MemberUpdateCommand(
    val id: ObjectId,
    val nickname: String
)