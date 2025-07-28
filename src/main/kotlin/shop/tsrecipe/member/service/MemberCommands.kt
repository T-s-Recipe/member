package shop.tsrecipe.member.service

import org.bson.types.ObjectId
import shop.tsrecipe.member.domain.OAuthInfo

data class SignUpCommand(
    val oAuthInfo: OAuthInfo,
    val name: String,
    val email: String?,
    val nickname: String
)

data class GetMemberQuery(
    val memberId: ObjectId?,
    val oauthInfo: OAuthInfo?
)