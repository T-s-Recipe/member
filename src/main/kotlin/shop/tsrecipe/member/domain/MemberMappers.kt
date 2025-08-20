package shop.tsrecipe.member.domain

import shop.tsrecipe.member.api.MemberResponse

fun Member.toResponse(): MemberResponse {
    return MemberResponse(
        id = this.id.toString(),
        oauthProvider = this.oauthInfo.provider,
        oauthId = this.oauthInfo.id,
        nickname = this.nickname,
    )
}