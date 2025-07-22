package shop.tsrecipe.member.domain

import shop.tsrecipe.member.api.MemberResponse

fun Member.toResponse(): MemberResponse {
    return MemberResponse(
        id = this.id.toString(),
        oauthProvider = this.oAuthInfo.provider,
        oauthId = this.oAuthInfo.id,
        name = this.name,
        email = this.email,
        nickname = this.nickname,
    )
}