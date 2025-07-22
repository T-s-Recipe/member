package shop.tsrecipe.member.service

import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Repository
import shop.tsrecipe.member.domain.Member
import shop.tsrecipe.member.domain.OAuthInfo
import shop.tsrecipe.member.repository.MemberRepository

@Repository
class MemberManager(
    private val memberRepository: MemberRepository
) {
    suspend fun create(command: SignUpCommand): Member? {
        return memberRepository.save(
            Member(
                oAuthInfo = command.oAuthInfo,
                name = command.name,
                email = command.email,
                nickname = command.nickname
            )
        ).awaitSingleOrNull()
    }
}