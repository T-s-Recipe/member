package shop.tsrecipe.member.domain

import shop.tsrecipe.member.api.MemberResponse

fun Member.toResponse(): MemberResponse {
    return MemberResponse(
        id = this.id.toString(),
        oAuthProvider = this.oAuthInfo.provider,
        oAuthId = this.oAuthInfo.id,
        nickname = this.nickname,
    )
}